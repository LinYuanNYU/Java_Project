var gameClient = null;
var gameFlag = false;
var curValue = 5;
var totalValue = 0;
var initialPlayerId = null;
var curPlayerId = 0;
var numOfPlayer = 0;
var gameStage = 0;
var foldedTotalValue = 0;
const Action = {FOLD: "FOLD", BET: "BET", RAISE: "RAISE"}

let curBetted = new Map();
let foldedPlayerIds = new Set();
let publicCards = null;
let handCards = null;
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
    numOfPlayer = len;
    var pos = 1;
    for (let i = counter; i < 6 + counter && i < counter + len; i++) {
        if (obj['players'][i%len]['id'] === myid) {
            append_str = "<p id='player0-name'>" + obj['players'][i%len]['id'] + "</p>" +
                "<p id='player0-money'>" + "current money: " + obj['players'][i%len]['money'] + "</p>";
            $("#player0").append(append_str);
            if (obj['players'][i%len]['id'] === obj['waitingForUserId']) {
                $("#player0-name-chips").css("border", "5px solid yellow");
                curPlayerId = 0;
                if (initialPlayerId === null) {
                    initialPlayerId = curPlayerId;
                }
            }
        } else {
            append_str = "<p id='player" + pos + "-name'>" + obj['players'][i%len]['id'] + "</p>" +
                "<p id='player" + pos + "-money'>" + "current money: " + obj['players'][i%len]['money'] + "</p>";
            $("#player" + pos).append(append_str);
            if (obj['players'][i%len]['id'] === obj['waitingForUserId']) {
                $("#player" + pos + "-name-chips").css("border", "5px solid yellow");
                curPlayerId = pos;
                if (initialPlayerId === null) {
                    initialPlayerId = curPlayerId;
                }
            }
            pos += 1;
        }
    }
    document.getElementById("board-bet").innerHTML = "Cur Bet: " + curValue;
}
function moveToNext() {
    for (let i = 0; i < curBetted.size; i++) {
        if (!foldedPlayerIds.has(i) && curValue - curBetted.get(i) !== 0) {
            return false;
        }
    }
    return true;
}
function handleActionMessage(obj) {
    updateClient(curPlayerId, obj['action'], obj['raiseAmount'])
    $("#player" + curPlayerId + "-name-chips").css("border", "0px solid yellow");
    curPlayerId++;
    curPlayerId = curPlayerId % numOfPlayer;
    if (numOfPlayer === foldedPlayerIds.size + 1) {
        alert("Game Over!")
    }
    while (foldedPlayerIds.has(curPlayerId)) {
        curPlayerId++;
        curPlayerId = curPlayerId % numOfPlayer;
    }
    $("#player" + curPlayerId + "-name-chips").css("border", "5px solid yellow");

    // If curPlayerId == 0: set bet button with amount
    if (!curBetted.has(curPlayerId)) {
        curBetted.set(curPlayerId, 0);
    }
    if (curPlayerId === 0) {
        document.getElementById("call-button").innerText = "CALL " + (curValue - curBetted.get(curPlayerId))
    }
    // If cur total money == cur bet * number of user: open first three cards
    if (moveToNext()) {
        // open first three cards
        if (gameStage === 0) {
            for (let i = 0; i < 3; i++) {
                let file = publicCards[i]['rankString'].toLowerCase() + "_of_" + publicCards[i]['suits'].toLowerCase() + "s.png";
                $("#flop" + (i + 1)).css("background-image", "url('./images/" + file + "')");
            }
            gameStage = 1;
        } else if (gameStage === 1) {
            let file = publicCards[3]['rankString'].toLowerCase() + "_of_" + publicCards[3]['suits'].toLowerCase() + "s.png";
            $("#turn").css("background-image", "url('./images/" + file + "')");
            gameStage = 2;
        } else if (gameStage === 2){
            let file = publicCards[4]['rankString'].toLowerCase() + "_of_" + publicCards[4]['suits'].toLowerCase() + "s.png";
            $("#river").css("background-image", "url('./images/" + file + "')");
            gameStage = 3;
        } else {
            // Competition
            // Evaluator Reference: https://github.com/chenosaurus/poker-evaluator/
            // If you have poker-evaluator installed, you can use that to compare who's winning!
            /*
            var PokerEvaluator = require("poker-evaluator");
            var pub = []
            var maxValue = 0;
            var maxName = null;
            var maxlist = null;
            for (let i = 0; i < publicCards.size; i++) {
                if (publicCards[i]['rankString'] === "10") {
                    str = "t" + publicCards[i]['suits'].charAt(0).toLowerCase();
                } else {
                    str = publicCards[i]['rankString'].charAt(0).toLowerCase() + publicCards[i]['suits'].charAt(0).toLowerCase();
                }
                pub.push(str);
            }
            for (let i = 0; i < numOfPlayer; i++) {
                if (!foldedPlayerIds.has(i)) {
                    var cur = JSON.parse(JSON.stringify(pub));
                    var name = document.getElementById("player" + i).innerText;
                    // compare
                    var cards = handCards['name'];
                    for (let i = 0; i < cards.size; i++) {
                        if (cards[i]['rankString'] === "10") {
                            str = "t" + cards[i]['suits'].charAt(0).toLowerCase();
                        } else {
                            str = cards[i]['rankString'].charAt(0).toLowerCase() + cards[i]['suits'].charAt(0).toLowerCase();
                        }
                        cur.push(str);
                        if (maxlist === null) {
                            maxlist = cur;
                        }
                        new_value = PokerEvaluator.evalHand(cur);
                        if (new_value['value'] > maxValue) {
                            maxValue = new_value['value'];
                            maxName = name;
                        }
                    }
                }
            }
            alert("After comparison, " + maxName + "has won the game"); */
            alert("Please install the below package and uncomment codes where generate this message, then you can get the winner automatically! reference: https://github.com/chenosaurus/poker-evaluator/")
        }
    }
}
function handleCardsResponseMessage(obj) {
    let cards = obj['handCards'][sessionStorage.getItem('userId')];
    for (let i = 0; i < cards.length; i++) {
        let card = cards[i];
        let file = card['rankString'].toLowerCase() + "_of_" + card['suits'].toLowerCase() + "s.png";
        console.log("change card image to " + file);
        $("#player0-card" + (i + 1)).css("background-image", "url('./images/" + file + "')");
    }
    publicCards = obj['publicCards'];
    handCards = obj['handCards'];
}
function gameCallBack(msg) {
    obj = JSON.parse(msg.body);
    console.log("Received a Multicast Message!!")
    console.log("------------------------------------------------------")
    console.log(JSON.stringify(obj, null, 2));
    console.log("------------------------------------------------------")
    if (obj['type'] === 'GameStartMessage') {
        handleGameStartMessage(obj);
    } else if (obj['type'] === 'ActionMessage') {
        handleActionMessage(obj);
    } else if (obj['type'] === "CardsResponseMessage") {
        handleCardsResponseMessage(obj);
    }
}
function fold() {
    if (curPlayerId === 0) {
        send_str = JSON.stringify({'userId': sessionStorage.getItem('userId'),
            'roomId': 1,
            'action': Action.FOLD})
        gameClient.send("/app/room/action", {}, send_str);
        console.log("Sending JSON to Server!")
        console.log("----------------------------------")
        console.log(send_str);
        console.log("----------------------------------")
    }
}

function disableUser(id) {
    $("#player" + id + "-card1").css("background-image", "url('./images/cardback.png')");
    $("#player" + id + "-card2").css("background-image", "url('./images/cardback.png')");
    document.getElementById("player" + id).innerText = "Folded"
}
function updateClient(id, action, amount) {
    if (action === Action.FOLD) {
        foldedPlayerIds.add(id);
        foldedTotalValue = totalValue;
        disableUser(curPlayerId);
        return;
    }
    var money_left = document.getElementById("player" + id + "-money").innerHTML.split("current money: ")[1];
    if (action === Action.RAISE) {
        curValue += amount;
        document.getElementById("board-bet").innerHTML = "Cur Bet: " + curValue;
    }
    if (!curBetted.has(id)) {
        curBetted.set(id, 0);
    }
    money_left -= (curValue - curBetted.get(id));
    if (money_left < 0) {
        disableUser(curPlayerId);
    } else {
        totalValue += (curValue - curBetted.get(id));
        curBetted.set(id, curValue);
        document.getElementById("player" + id + "-money").innerHTML = "current money: " + money_left;
        document.getElementById("board-money").innerHTML = "Money: " + totalValue;
    }
}
function call() {
    if (curPlayerId === 0) {
        send_str = JSON.stringify({'userId': sessionStorage.getItem('userId'),
            'roomId': 1,
            'action': Action.BET})
        gameClient.send("/app/room/action", {}, send_str);
        console.log("Sending JSON to Server!")
        console.log("----------------------------------")
        console.log(send_str);
        console.log("----------------------------------")
    }
}
function raise() {
    if (curPlayerId === 0) {
        let selector = document.getElementById("raise-options")
        let amount = selector.options[selector.selectedIndex].text
        let send_str = JSON.stringify({'userId': sessionStorage.getItem('userId'),
            'roomId': 1,
            'action': Action.RAISE,
            'raiseAmount': amount})
        console.log("Sending JSON to Server!")
        console.log("----------------------------------")
        console.log(send_str);
        console.log("----------------------------------")
        gameClient.send("/app/room/action", {}, send_str);
    }
}
function startGame() {
    if (gameFlag === false) {
        send_str = JSON.stringify({'userId': sessionStorage.getItem('userId'), 'roomId': 1});
        console.log("Sending JSON to Server!")
        console.log("----------------------------------")
        console.log(send_str);
        console.log("----------------------------------")
        gameClient.send("/app/room/start", {}, send_str);
        gameClient.send("/app/room/pull_cards", {}, send_str);
        gameFlag = true;
    }
}
$(function () {
    gameClientConnect();
    $( "#start-button" ).click(function() { startGame(); });
    $( "#fold-button" ).click(function() { fold(); });
    $( "#call-button" ).click(function() { call(); });
    $( "#raise-button" ).click(function() { raise(); });
    var logger = document.getElementById('my-log');
    console.log = function (message) {
        if (typeof message == 'object') {
            logger.innerText += (JSON && JSON.stringify ?
                JSON.stringify(message, null, 2) : JSON.stringify(JSON.parse(message), null, 2));
        } else {
            try {
                msg = JSON.parse(message);
                message = JSON.stringify(msg, null, 2)
                logger.innerText += message + '\n';
            } catch (e) {
                logger.innerText += message + '\n';
            }
        }
        logger.scrollTo(0, logger.scrollHeight);
    }
});