var loginClient = null;
var members = [];
function loginClientConnect() {
    var socket = new SockJS('/java_project');
    loginClient = Stomp.over(socket);
    loginClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        loginClient.subscribe('/topic/game_room', loginCallback);
    });
}

function joinRoom() {
    loginClient.send("/app/room/join", {}, JSON.stringify({'userId': $("#name").val(),
                                                                 'roomId': 1,
                                                                 'money': $("#money").val()}));
}
function loginCallback(msg) {
    obj = JSON.parse(msg.body);
    if (obj.hasOwnProperty("gameStartFlag") && obj['gameStartFlag'] === true) {
        sessionStorage.setItem('userId', $("#name").val())
        window.location.href = "./game.html";
    } else {
        showGreeting(obj)
    }
}

function showGreeting(obj) {
    for (let i = 0; i < obj['members'].length; i++) {
        if (!members.includes(obj['members'][i]['id'])) {
            members.push(obj['members'][i]['id']);
            message = "User: " + obj['members'][i]['id'] +" joined the waiting room with " + obj['members'][i]['money']
            $("#greetings").append("<tr><td>" + message + "</td></tr>");
        }
    }

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
    $( "#send" ).click(function() { joinRoom(); });
});

