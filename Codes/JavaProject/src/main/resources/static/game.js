var gameClient = null;
var gameFlag = false;
var curValue = 5;
var curPlayerId = 0;
function gameClientConnect() {
    var socket = new SockJS('/java_project');
    gameClient = Stomp.over(socket);
    gameClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        gameClient.subscribe('/topic/in_game', gameCallBack);
    });
}
function handleGameStartMessage(obj) {
    myid = sessionStorage.getItem('userId');
    var counter = 0;
    while (obj['players'][counter]['id'] !== myid) {
        counter += 1;
    }
    let len = obj['players'].length;
    var pos = 1;
    for (let i = counter; i < 6 + counter && i < counter + len; i++) {
        console.log(obj['players'][i%len]['id'])
        if (obj['players'][i%len]['id'] === myid) {
            append_str = "<p id='player0-name'>" + obj['players'][i%len]['id'] + "</p>" +
                "<p id='player0-monry'>" + "current money: " + obj['players'][i%len]['money'] + "</p>";
            $("#player0").append(append_str);
            if (obj['players'][i%len]['id'] === obj['waitingForUserId']) {
                $("#player0-name-chips").css("border", "5px solid yellow");
            }
        } else {
            append_str = "<p id='player" + pos + "-name'>" + obj['players'][i%len]['id'] + "</p>" +
                "<p id='player" + pos + "-money'>" + "current money: " + obj['players'][i%len]['money'] + "</p>";
            $("#player" + pos).append(append_str);
            if (obj['players'][i%len]['id'] === obj['waitingForUserId']) {
                $("#player" + pos + "-name-chips").css("border", "5px solid yellow");
            }
            pos += 1;
        }

    }
}
function handleActionMessage(obj) {
    const Action = {FOLD: "FOLD", BET: "BET", RAISE: "RAISE"}
    $("#player" + curPlayerId + "-name-chips").css("border", "0px solid yellow");
    curPlayerId++;
    
    $("#player" + curPlayerId + "-name-chips").css("border", "0px solid yellow");
}
function gameCallBack(msg) {
    obj = JSON.parse(msg.body);
    if (obj['type'] === 'GameStartMessage') {
        handleGameStartMessage(obj);
    } else if (obj['type'] === 'ActionMessage') {
        handleActionMessage(obj);
    }
}
function fold() {

}
function call() {
    const Action = {FOLD: "FOLD", BET: "BET", RAISE: "RAISE"}
    send_str = JSON.stringify({'userId': sessionStorage.getItem('userId'),
                                     'roomId': 1,
                                     'action': Action.BET})
    gameClient.send("/app/room/action", {}, send_str);
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
    $( "#fold-button" ).click(function() { fold(); });
    $( "#call-button" ).click(function() { call(); });
});