var stompClient = null;

function connect() {

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        // var from = document.getElementById('from').value;

        // 사용자 개인 큐 주제를 구독
        stompClient.subscribe('/user/' + from + '/queue/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });

    });
}

function disconnect(){
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");

    var from = document.getElementById('from').value;
    var messages = document.getElementById('messages');
    var messageElement = document.createElement('div');
    messageElement.appendChild(document.createTextNode(from + " 연결 해제되었습니다."));
    messages.appendChild(messageElement);
}

function sendMessage() {
    var from = document.getElementById('from').value;
    var to = document.getElementById('to').value;  // 수신자
    var content = document.getElementById('content').value;
    stompClient.send("/app/chat", {}, JSON.stringify({'from': from, 'to': to, 'content': content}));

    var messages = document.getElementById('messages');
    var messageElement = document.createElement('div');
    messageElement.appendChild(document.createTextNode("나 : " + content));
    messages.appendChild(messageElement);
}

function showMessage(message) {
    var messages = document.getElementById('messages');
    var messageElement = document.createElement('div');
    messageElement.appendChild(document.createTextNode(message.from + ": " + message.content));
    messages.appendChild(messageElement);
}

window.onload = function() {
    if([[${receiverNo}]] == 0){
        alert('메세지 상대 없음')
    }
};

window.onresize=()=>{
    var innerwidth = window.innerWidth
    const chatting_list1 = document.getElementById('chatting_list_1');

    innerwidth <= '950'?chatting_list1.style.display='none':chatting_list1.style.display='flex';
};