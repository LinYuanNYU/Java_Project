var loginClient = null;

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
function loginClientConnect() {
    var socket = new SockJS('/java_project');
    loginClient = Stomp.over(socket);
    loginClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        loginClient.subscribe('/topic/game_room', loginCallback);
    });
}

function sendName() {
    loginClient.send("/app/room/join", {}, JSON.stringify({'userId': $("#name").val(), 'roomId': 1}));
}
function loginCallback(msg) {
    obj = JSON.parse(msg.body);
    if (obj.hasOwnProperty("gameStartFlag") && obj['gameStartFlag'] === true) {
        sessionStorage.setItem('userId', $("#name").val())
        window.location.href = "./game.html";
    }
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}
function start() {
    loginClient.send("/app/room/start", {}, JSON.stringify({'userId': $("#name").val(), 'roomId': 1}));
}
function gameStart() {
    loginClient.send("/app/room/start", {}, JSON.stringify({'userId': $("#name").val(), 'roomId': 1}));
    sessionStorage.setItem('userId', $("#name").val())
    window.location.href = "./game.html";
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    loginClientConnect();
    $( "#send" ).click(function() { sendName(); });
});

