let stompClient = null;

const chatLineElementId = "chatLine";
const roomIdElementId = "roomId";
const messageElementId = "message";
const sendElementId = "send";

const specialRoomId = 1408;

const isSpecialRoom = (roomId) => Number(roomId) === specialRoomId;

const setConnected = (connected) => {
    const connectBtn = document.getElementById("connect");
    const disconnectBtn = document.getElementById("disconnect");

    connectBtn.disabled = connected;
    disconnectBtn.disabled = !connected;
    const chatLine = document.getElementById(chatLineElementId);
    chatLine.hidden = !connected;
}

const setSendEnabled = (enabled) => {
    document.getElementById(messageElementId).disabled = !enabled;
    document.getElementById(sendElementId).disabled = !enabled;
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        document.getElementById(chatLineElementId).replaceChildren();
        setConnected(true);
        const userName = frame.headers["user-name"];
        const roomId = document.getElementById(roomIdElementId).value;
        const specialRoom = isSpecialRoom(roomId);
        setSendEnabled(!specialRoom);
        console.log(`Connected to roomId: ${roomId} frame:${frame}`);
        const topicName = `/topic/response.${roomId}`;
        const topicNameUser = `/user/${userName}${topicName}`;
        const onMessage = (message) => showMessage(formatMessage(JSON.parse(message.body), specialRoom));
        stompClient.subscribe(topicName, onMessage);
        stompClient.subscribe(topicNameUser, onMessage);
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    setSendEnabled(true);
    console.log("Disconnected");
}

const sendMsg = () => {
    const roomId = document.getElementById(roomIdElementId).value;
    if (isSpecialRoom(roomId)) {
        console.log(`Sending messages to roomId: ${roomId} is forbidden`);
        return;
    }
    const message = document.getElementById(messageElementId).value;
    stompClient.send(`/app/message.${roomId}`, {}, JSON.stringify({'messageStr': message}))
}

const formatMessage = (message, withRoom) => withRoom ? `[room ${message.roomId}] ${message.messageStr}` : message.messageStr;

const showMessage = (message) => {
    const chatLine = document.getElementById(chatLineElementId);
    let newRow = chatLine.insertRow(-1);
    let newCell = newRow.insertCell(0);
    let newText = document.createTextNode(message);
    newCell.appendChild(newText);
}
