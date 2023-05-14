const socket = io();

const welcome = document.getElementById("welcome");
const form = welcome.querySelector("form");
const room = document.querySelector("#room");

room.hidden = true;

const roomName = document.querySelector('#chatroom .room-title').textContent;
const username = document.querySelector("#username").textContent;

function handleMessageSubmit(event) {
    event.preventDefault();
    const input = room.querySelector("#msg input");
    const value = input.value;
    //시간을 'HH:mm'형식으로 출력하기 위함.
   // const currentTime = new Intl.DateTimeFormat('en-US',{month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'}).format(new Date());
   const timestamp = new Date();
    socket.emit("new_message", value,roomName,timestamp,() => {
        const formattedTimestamp = new Intl.DateTimeFormat('en-US',{month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'}).format(timestamp);
        addMessage(`You: ${value} (${formattedTimestamp})`);
    });
    input.value = "";
}

function addMessage(message) {
    const ul = room.querySelector("ul");
    const li = document.createElement("li");
    li.innerText = message;
    //console.log(message);
    if(message.includes("You :")){
        li.classList.add("my-message");
    }
    // 현재 스크롤 위치와 스크롤 높이를 확인하여 스크롤이 맨 하단에 있는지 확인
  const isScrolledToBottom = ul.scrollHeight - ul.clientHeight <= ul.scrollTop + 1;
    ul.appendChild(li);

    // 스크롤이 맨 하단에 있을 경우, 자동으로 스크롤 이동
  if (isScrolledToBottom) {
    ul.scrollTop = ul.scrollHeight;
  }
}

function addUser(message) {
    const ul = document.querySelector("#messages-container").querySelector("ul");
    const li = document.createElement("li");
    li.innerText = message; 
    ul.appendChild(li);
}

//안씀
function handleNicknameSubmit(event){
    event.preventDefault();
    const input = room.querySelector("#name input");
    const value = input.value;

    socket.emit("nickname", username);  //닉네임 지정하는곳
}

function showRoom(){
    welcome.hidden = true;
    room.hidden =false;
    const h3 = room.querySelector("h3");
    //h3.innerText = `${roomName}`;  //입장했을때 방제목 바뀜
    const msgForm = room.querySelector("#msg");
    socket.emit("nickname", username);
    //const nameForm = room.querySelector("#name");
    msgForm.addEventListener("submit", handleMessageSubmit);
    //nameForm.addEventListener("submit", handleNicknameSubmit);
    
}
function handleRoomSubmit(event) {
    event.preventDefault();
    //const chatroom = roomName.textContent; 
    const name = roomName+","+username;
    socket.emit("enter_room",name,showRoom);
    //roomName = chatroom;
    //input.value = "";

}

//채팅목록처리
/*function getUserRooms(){
    socket.emit("get_user_rooms");
}*/

function showUserRooms(rooms){
    const userRoomsList = document.querySelector("#userRooms ul");
    userRoomsList.innerHTML = "";

    rooms.forEach((room)=>{
        const li = document.createElement("li");
        li.innerText = room;
        userRoomsList.appendChild(li);
    });
}

window.addEventListener("load",() => {
    // 채팅방 목록을 불러옵니다.
loadChatrooms();
})

form.addEventListener("submit", handleRoomSubmit);

const urlParams = new URLSearchParams(window.location.search);
const nickname = urlParams.get("nickname");

// 채팅방 목록을 요청하고 화면에 표시하는 함수
function loadChatrooms() {
  socket.emit("request_chatrooms", nickname, (chatrooms) => {
    // 채팅방 목록을 화면에 표시하는 코드를 작성합니다.
    // 예를 들어, 채팅방 목록을 HTML의 `ul` 요소에 추가할 수 있습니다.
    const chatroomList = document.getElementById("chatroom-list");
    chatroomList.innerHTML = ""; // 기존 목록을 비웁니다.

    chatrooms.forEach((chatroom) => {
      const li = document.createElement("li");
      li.textContent = chatroom;
      li.addEventListener("click", () => {
        // 채팅방을 클릭했을 때 이동하는 코드를 작성합니다.
        // 예를 들어, 채팅방 페이지로 이동할 수 있습니다.
        window.location.href = `http://118.67.142.45:3000?title=${encodeURIComponent(chatroom)}&nickname=${encodeURIComponent(nickname)}`;
      });
      chatroomList.appendChild(li);
    });
  });
}




socket.on("welcome",(user, newCount)=>{
    const h3 = room.querySelector("h3");
    const roomName1 = document.querySelector('#chatroom').textContent;
    h3.innerText = `${roomName1} (현재 입장수: ${newCount})`;
    addMessage(`${username} 님이 입장하셨습니다.`);  //원래는 addMessage()
});

//채팅기록을 출력하는 이벤트 처리
socket.on("load_chat_history", (messages) => {
    const ul = document.querySelector("#messages-container");
    //const ul = room.querySelector("ul");
    messages.forEach((msg) =>{
        const formattedTimestamp = new Intl.DateTimeFormat('en-US',{month: '2-digit', day: '2-digit',  hour: '2-digit', minute: '2-digit'}).format(new Date(msg.timestamp));
        const li = document.createElement("li");
        //현재 사용자의 닉네임과 메시지의 보낸이가 같을 경우, 보낸이를 'you'로 변경
        const sender = msg.sender === nickname ? "You" : msg.sender;
        //li.innerText = `${msg.user} (${new Date(msg.timestamp).toLocaleString()}) : ${msg.message}`;
        li.innerText = `${sender} : ${msg.message} (${formattedTimestamp})`
        ul.appendChild(li);
       
    });
    //채팅 기록을 업데이트한 후에 스크롤을 맨 아래로 이동시키기
    /*setTimeout(() => {
        const messagesContainer = document.querySelector("#messages-container");
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    },0); */

    //채팅 기록이 로드된 후 함수 호출
    ul.scrollTop = ul.scrollHeight;    
});

//메세지 추가 코드 부분
socket.on("message", (msg, sender, timestamp) => {
    //const formattedTimestamp = new Intl.DateTimeFormat('en-US',{month: '2-digit', day: '2-digit',  hour: '2-digit', minute: '2-digit'}).format(timestamp);
    
    addMessage(`${sender}: ${msg} (${timestamp})`);
    scrollToBottom(); //새 메시지가 추가될 때마다 호출

   
})

socket.on("bye",(left, newCount)=>{
    const h3 = room.querySelector("h3");
    h3.innerText = `Room ${roomName} (${newCount})`;
    addMessage(`${left}님이 떠나셨습니다.`);
});

socket.on("new_message", (msg, timestamp) => {
   //const formattedTimestamp = new Intl.DateTimeFormat('en-US',{month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'}).format(timestamp);
    addMessage(`${socket.nickname}: ${msg} (${timestamp})`);
})

socket.on("room_change", (rooms) =>{
    showUserRooms(rooms);
    const roomList = welcome.querySelector("ul");
    roomList.innerHTML = "";
    if(rooms.length ===0){
        return;
    }
    rooms.forEach((room) => {
        const li = document.createElement("li");
        li.innerText = room;
        li.addEventListener("click", () => {  //li,즉,채팅방목록중 하나를 클릭했을때 이동.
            socket.emit("enter_room",room,showRoom);
        });
        roomList.append(li);
    });
});

//채팅방의 입장수를 정확하게 보여주기
socket.on("room_count",(count) =>{
    const h3 = room.querySelector("h3");
    h3.innerText = `${roomName} (현재 입장수: ${count})`;
});

//스크롤이 자동으로 맨 아래로 이동하도록 하는 코드
function scrollToBottom(){
    const messagesContainer = document.getElementById('messages-container');
    messagesContainer.scrollTop = messagesContainer.scrollerHeight;
}

const userChatrooms = {
    
}



/*
const p = document.querySelector("#username");


async function getUserInfo() {
    try {
      // Spring Boot API 엔드포인트를 호출
      const response = await axios.get('http://localhost:8082/teams/userInfo');
       // p.innerText = response.data.team_title;
      // 로그인한 사용자의 정보 출력
      console.log(response.data);
    } catch (error) {
      console.error('Error fetching user info:', error);
    }
  }

 // DOMContentLoaded 이벤트 리스너를 추가하여 DOM이 완전히 로드된 후 getUserInfo() 호출
document.addEventListener('DOMContentLoaded', () => {
    getUserInfo();
});

*/










/*
const messageList = document.querySelector("ul");
const nickForm = document.querySelector("#nick");
const messageForm = document.querySelector("#message");
//app.js의 socket은 서버로의 연결을 뜻함
const socket = new WebSocket(`ws://${window.location.host}`);

function makeMessage(type, payload) {
    const msg = {type, payload};
    return JSON.stringify(msg);
}

//socket이 open되었다면, 브라우저에 연결되었다고 로그 출력
socket.addEventListener("open", () => {
    console.log("connected to Server!");
});

socket.addEventListener("message",(message) =>{
    const li = document.createElement("li");
    li.innerText = message.data;
    messageList.append(li);
});

socket.addEventListener("close", () => {
    console.log("Disconnected from server!");
});


function handleMessageSubmit(event) {
    //preventDafault : a태그나 submit태그는 누르게 되면 href를 통해 이동하거나 창이 새로고침되어 실행 <--이를 막아줌
    event.preventDefault();
    const input = messageForm.querySelector("input");
    socket.send(makeMessage("new_message",input.value));
    const li = document.createElement("li");
    li.innerText = `You: ${input.value}`;
    messageList.append(li);
    input.value = "";
}
messageForm.addEventListener("submit", handleMessageSubmit);

function handleNickSubmit(event) {
    event.preventDefault();
    const input = nickForm.querySelector("input");
    socket.send(makeMessage("nickname",input.value));
    input.value = "";
}

nickForm.addEventListener("submit", handleNickSubmit);
 */