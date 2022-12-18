var gameClient = null;
var gameFlag = false;
function gameClientConnect() {
    var socket = new SockJS('/java_project');
    gameClient = Stomp.over(socket);
    gameClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        gameClient.subscribe('/topic/in_game', gameCallBack);
    });
}

function gameCallBack(msg) {
    obj = JSON.parse(msg.body);
    myid = sessionStorage.getItem('userId');
    var counter = 0;
    while (obj['players'][counter]['id'] !== myid) {
        counter += 1;
    }
    let len = obj['players'].length;
    var pos = 1;
    for (let i = counter; i < 6 + counter && i < counter + len; i++) {
        console.log(obj['players'][i%len]['id'])
        append_str = "<p>" + obj['players'][i%len]['id'] + "</p>" +
            "<p>" + "current money: " + obj['players'][i%len]['money'] + "</p>";
        if (obj['players'][i%len]['id'] === myid) {
            $("#player0").append(append_str);
            if (obj['players'][i%len]['id'] === obj['waitingForUserId']) {
                $("#player0-name-chips").css("border", "5px solid yellow");
            }
        } else {
            $("#player" + pos).append(append_str);
            if (obj['players'][i%len]['id'] === obj['waitingForUserId']) {
                $("#player" + pos + "-name-chips").css("border", "5px solid yellow");
            }
            pos += 1;
        }

    }
}
function startGame() {
    if (gameFlag == false) {
        gameClient.send("/app/room/start", {},
            JSON.stringify({'userId': sessionStorage.getItem('userId'), 'roomId': 1}));
        gameFlag = true;
    }
}
$(function () {
    gameClientConnect();
    $( "#start-button" ).click(function() { startGame(); });
});