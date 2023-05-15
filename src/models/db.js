const mysql = require('mysql2/promise');
const MESSAGE_LIMIT = 100;
const pool = mysql.createPool({
    host: '118.67.142.45',
    user: 'admin',
    password: 'admin1234',
    database: 'webdb',
    /*host: 'localhost',
    user: 'root',
    password: '1234',
    database: 'webdb',*/
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
});

//채팅메세지를 db에 저장하는 함수
async function saveMessage(roomName, user_id, message, timestamp){

    // 해당 채팅방의 메시지 수 확인
    const countQuery = 'SELECT COUNT(*) as messageCount FROM message WHERE room_title = ?';
    const [countRows] = await pool.query(countQuery, [roomName]);
    const messageCount = countRows[0].messageCount;

    // 메시지 수가 제한을 초과하면 가장 오래된 메시지 삭제
    if (messageCount >= MESSAGE_LIMIT) {
        const deleteQuery = 'DELETE FROM message WHERE room_title = ? ORDER BY timestamp ASC LIMIT 1';
        await pool.query(deleteQuery, [roomName]);
    }
    const query = 'INSERT INTO message (room_title, sender, message) VALUES (?, ?, ?)';
    await pool.query(query, [roomName, user_id,message]);
    
}



//채팅 기록을 검색하는 함수
async function getChatHistory(roomName){
    const query = 'SELECT * FROM message WHERE room_title = ? ORDER BY timestamp ASC';
    const [rows] = await pool.query(query, [roomName]);
    return rows;
}

// 채팅방 목록을 저장하는 함수
async function saveUserChatroom(userId, roomName){
    const query = 'INSERT INTO chatrooms (userId, roomTitle) VALUES (?, ?) ON DUPLICATE KEY UPDATE userId = VALUES(userId), roomTitle = VALUES(roomTitle);';
  await pool.query(query, [userId, roomName]);
}

// 채팅방 목록을 불러오는 함수
async function getUserChatrooms(userId) {
    const query = 'SELECT roomTitle FROM chatrooms WHERE userId = ?';
    const [rows] = await pool.query(query, [userId]);
    return rows.map(row => row.roomTitle);
  }

  //유저가 채팅방을 나갈 때 chatrooms 테이블에서 해당 항목을 삭제하는 함수
async function removeUserChatroom(userId, roomName){
    const query= 'DELETE FROM chatrooms WHERE userId = ? AND roomTitle = ?';
    await pool.query(query, [userId, roomName]);
}

//새로운 메시지가 도착했는지 확인하는 함수
async function isNewMessage(roomName, timestamp){
    const query = 'select * from message where roomTitle = ? ORDER BY timestamp DESC LIMIT 1';
    await pool.query(query,[roomName]);
    // 가장 최근의 메시지의 타임스탬프가 새 메시지의 타임스탬프보다 이전이라면, 새 메시지가 도착했다고 판단
    return rows[0].timestamp < timestamp;
}

module.exports = {
    saveMessage,
    getChatHistory,
    saveUserChatroom,
    getUserChatrooms,
    removeUserChatroom,
    isNewMessage
};