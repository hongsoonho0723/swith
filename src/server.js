import express from "express";
import * as http from "http";

/*import WebSocket from "ws";*/
import {Server} from "socket.io"
import {instrument} from "@socket.io/admin-ui";

const app = express();
const pug = require("pug");
const path = require('path'); // path는 Node.js의 기본 내장 모듈이므로 require구문 추가
const cors = require('cors');
const bodyParser = require('body-parser'); //JSON형식의 본문 파싱을 위해  npm install body-parser 설치하고 변수 설정
let chatroomName ="";
let nickname="";

app.use(cors({
    origin: 'http://118.67.142.45:8082'
}));

app.set("view engine", "pug");
app.set("views",__dirname + "/views");
app.use("/public", express.static(__dirname+ "/public"));
app.use(express.json()); //JSON 형식의 본문 파싱
app.use(express.urlencoded({extended: false}));
/*
app.post("/",function(req,res){
    chatroomName = req.body.chatroom;
    console.log('chatroomName', chatroomName);
  res.redirect("/chatt");

})
app.get("/", function(req, res){
    console.log('get요청 실행');
    res.render("home");
    console.log('render완료');
   
} );
*/

//app.get("/*",(req,res)=>res.redirect("/"));

app.get('/', (req, res) => {
     chatroomName = req.query.title;
     nickname= req.query.nickname;
        console.log(chatroomName);
     //const chatTemplate = pug.compileFile('views/chat.pug');
     //const renderedHTML = chatTemplate({chatRoomTitle, teamTitle});
   // res.send(renderedHTML);
     
   res.render('home', {title: chatroomName , userNickname: nickname});
  });


//db 연결
const db = require('./models/db');

//http,websocket 둘다 돌리는 과정
const httpServer = http.createServer(app);
const wsServer = new Server(httpServer);
    /*, {
    cors: {
        origin: ["https://admin.socket.io"],
        credentials: true
    }
});
instrument(wsServer,{
    auth: false
})*/

//public rooms를 찾아주는 function
function publicRooms() {
    const sids = wsServer.sockets.adapter.sids;
    const rooms = wsServer.sockets.adapter.rooms;

    const publicRooms = [];
    rooms.forEach((_,key) => {
        if(sids.get(key) == undefined){
            publicRooms.push(key);
        }
    });
    return publicRooms;
}

function countRoom(roomName){
  return  wsServer.sockets.adapter.rooms.get(roomName)?.size;
}

wsServer.on("connection", (socket) =>{  //웹소켓 연결 시 / (socket) =>{}은 서버와 클라이언트의 소켓이 연결되었을 때 실행됩니다.
    socket["nickname"] = "Anon";
        socket.onAny((event) => {
            console.log(wsServer.sockets.adapter);
            console.log(`Socket Evnet:${event}`);
        })

        //채팅방 목록을 요청하는 이벤트 리스너
    socket.on("request_chatrooms", async (userId, callback) => {
        console.log("request_chatrooms 실행");
        const chatrooms = await db.getUserChatrooms(userId);
        console.log(chatrooms);
        callback(chatrooms);
    })


    socket.on("enter_room", async (name, done) => {  //클라이언트에서 enter_room 이벤트 요청시, 요청을 처리할 이벤트 리스너
        //roomName이라는 방에 참가
        const names = name.split(",");
        const roomName = names[0];
        const username = names[1];
        socket.join(roomName);  //클라이언트에서 enter_room 요청이 들어오면 socket.join(roomName) 실행
        await db.saveUserChatroom(username, roomName);
        const messages = await db.getChatHistory(roomName); //채팅방의 이전 메세지 기록을 가져옴
        //console.log("server messages:",messages);
        socket.emit("load_chat_history",messages); //이전 메세지 기록을 클라이언트에게 전달

        done();
        
        //Update userRooms
        if(!userRooms[socket.nickname]){
            userRooms[socket.nickname] = [];
        }
        userRooms[socket.nickname].push(roomName);

   /*     // Send chat history to the user
      if (chatHistory[roomName]) {
        chatHistory[roomName].forEach((message) => {
            socket.emit("new_message", message);
        });
    } 
    */

        socket.to(roomName).emit("welcome", socket.nickname, countRoom(roomName));
        wsServer.sockets.emit("room_change", publicRooms());
        wsServer.to(roomName).emit("room_count",countRoom(roomName)); //유저가 입장할때 입장수 갱신

    });

    

    socket.on("disconnect", () => {
        socket.rooms.forEach((room)=>{
            wsServer.to(room).emit("room_count",countRoom(rooom)-1); //유저가 연결 종료할 때.
        });
        wsServer.sockets.emit("room_change", publicRooms());
    })
    //유저가 채팅방에서 나갈 때 이 객체를 업데이트해야 한다.
    socket.on("disconnecting", () =>{
        socket.rooms.forEach((room) => {
            socket.to(room).emit("bye",socket.nickname, countRoom(room) -1);
        if(userRooms[socket.nickname]){
            userRooms[socket.ncikname] = userRooms[socket.nickname].filter((r)=> r!== room);
            }
        });
    });

    /*socket.on("get_user_room", () => {
        const rooms = userRooms[socket.nickname] || [];
        socket.emit("user_rooms",rooms);
    })*/

    socket.on("new_message", async (msg, room, timestamp, done) => {
       /* const formattedTimestamp = new Date(timestamp).toISOString().slice(0, 19).replace('T', ' '); //시간수정 new Date(timestamp줘야함)*/
        const formattedTimestamp = new Intl.DateTimeFormat('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            timeZone: 'Asia/Seoul'
        }).format(new Date(timestamp));
        //await db.saveMessage(room, socket.nickname, msg, formattedTimestamp);
        await db.saveMessage(room, socket.nickname, msg, formattedTimestamp);

        
        socket.to(room).emit("message", msg ,socket.nickname, timestamp);
       done();
        /*const message = `${socket.nickname}: ${msg}`;
        addMessageToHistory(room, message);
        socket.to(room).emit("new_message", message); */
        //done();
    });

    socket.on("nickname",(nickname) => (socket["nickname"] = nickname));

    socket.on("leave_room", (roomName,callback) =>{
        //연결을 끊고 채팅방을 떠는 코드
        socket.leave(roomName);

        const userId = socket.userNickname;

        db.removeUserChatroom(userId, roomName).then(()=>{
            console.log(`${userId}가 ${roomName}을 떠나심.`);
        }).catch(error => {
            console.error('방 삭제 에러: ',error);
        });
        callback();
    })

});

const handleListen = () => console.log(`Listening on http://118.67.142.45:3000`);

httpServer.listen(3000, handleListen);



/*
const wss = new WebSocket.Server({ server });

//fake database
const sockets=[];

//server.js의 socket은 연결된 브라우저를 뜻함
wss.on("connection", (socket) => {
    //소켓이 연결될때마다 sockets배열에 추가 -> 이로인해서 소켓끼리 통신가능
    sockets.push(socket);
    //닉넴을 정하지 않은 사람을 익명으로 지정
    socket["nickname"] = "Anon";
    //브라우저랑 연결되면 아래 콘솔 출력
    console.log("Connected to Browser!")
    //socket에 있는 메서드를 사용해보자(wss서버에 있는 메서드가 아님!!)
    //브라우저가 꺼지면 아래 콘솔 출력
    socket.on("close", () => console.log("Disconnected from the Browser"));
    //브라우저가 서버에 메시지 보내면 아래 콘솔 출력
    socket.on("message", (msg) => {
        const message = JSON.parse(msg);
        switch (message.type){
            case "new_message":
                const messageString = message.payload.toString('utf8');
                sockets.forEach(aSocket => aSocket.send(`${socket.nickname}: ${message.payload}`
                ));
            case "nickname":
                socket["nickname"] = message.payload;
        }
    });
});
*/


//서버에 대화내용 저장
const chatHistory = {};
function addMessageToHistory(roomName, message) {
    if (!chatHistory[roomName]) {
        chatHistory[roomName] = [];
    }
    chatHistory[roomName].push(message);
}

//서버에 채팅목록 저장 및 업데이트
const userRooms={}; //{username: [room1, room2, ....]}



