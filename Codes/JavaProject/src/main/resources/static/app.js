var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}
function connect() {
    var socket = new SockJS('/java_project');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/game_room', func);
    });
}

function sendName() {
    stompClient.send("/app/room/join", {}, JSON.stringify({'userId': $("#name").val(), 'roomId': 1}));
}
function func(greeting) {
    receivedObject = JSON.parse(greeting.body);
    console.log(receivedObject['type']);
    showGreeting("Hello user " + JSON.stringify(receivedObject['ownerId']) + ", ready in owner: " + JSON.stringify(receivedObject['ownerId']) + "'s room " + JSON.stringify(receivedObject['roomId']));
}
function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}
function start() {
    stompClient.send("/app/room/start", {}, JSON.stringify({'userId': $("#name").val(), 'roomId': 1}));
}
function gameStart() {
    window.location.replace("./game.html");
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    connect();
    $( "#send" ).click(function() { sendName(); });
});

