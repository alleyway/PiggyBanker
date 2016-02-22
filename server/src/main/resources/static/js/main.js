$(function () {
    var sessionId;

    var container = $("#barcode_container");

    var amountDiv = $("#amount");

    var blinkImg = $("#piggy_blink");

    var coinOrigWidth = $("#coin").width();

    // defined a connection to a new socket endpoint
    var socket = new SockJS('/stomp');

    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        // subscribe to the /topic/message endpoint
        stompClient.subscribe("/topic/message", function (data) {
            var message = JSON.parse(data.body);
            if (message.gameId == sessionId) {
                console.log("we're starting a new game, set up UI..");
                //TODO: reset UI
                $("#instructions_row").fadeOut(1000, function(){
                    $("#pig_row").fadeIn(500);
                });
            }

            if (message.status == "RESET") {
                animateOut(false);
                window.alert("Game over, Great job!");
                resetStartCode();
                $("#pig_row").fadeOut(1000, function(){
                    $("#instructions_row").fadeIn(500);
                });

            }

            if (message.status == "HEARTBEAT") {
                blink();
            }
            if (message.sessionId != sessionId) return;
            container.empty();
            $("<img/>", {
                src: "data:image/svg+xml;base64," + btoa(message.barcodeContent)
            }).appendTo(container);
            amountDiv[0].innerHTML = "$" + message.amount;

            animateOut(true);
        });
    });

    resetStartCode();

    function resetStartCode() {
        var startCodeContainer = $("#startcode_container");

        startCodeContainer.empty();

        sessionId = new Date().getTime();
        //create a new session by using the time, hopefully no collisions

        $("<img/>", {
            src: "/api/game/startcode/" + sessionId,
            class: "img-responsive"
        }).appendTo(startCodeContainer);

        startCodeContainer.attr("href", "/api/game/start/" + sessionId);

    }

    function animateIn() {
        var coin = $("#coin");
        var left = -.85 * $(window).width();

        console.log("left: " + left);
        coin.css({left: left, opacity: 0.25, rotation: 0}).animate({"left": "0px", opacity: 1.0, rotation: 360}, {
            duration: 700,
            easing: "easeInOutQuad",
            step: function (angle, fx) {
                if (fx.prop != "rotation") return;
                //console.log("angle: " + angle);
                $(this).css({
                    "-moz-transform": "rotate(" + angle + "deg)",
                    "-webkit-transform": "rotate(" + angle + "deg)",
                    "-ms-transform": "rotate(" + angle + "deg)",
                    "transform": "rotate(" + angle + "deg)"
                });
            },
            complete: function() {
                //window.setTimeout(animateOut, 1000);
            }
        });
    }

    function animateOut(thenAnimateIn) {
        var coin = $("#coin");
        var top = 0;

        //ratio of pig hole to pig image width
        var slotWidth = (150/785) * $("#piggy_bank").width() * .9;

        coin.css({top: top, width: coinOrigWidth}).animate({"top": "250px", width: slotWidth}, {
            duration: 700,
            easing: "easeInOutExpo",
            complete: function() {
                $(this).css( {opacity: 0.0, top: 0, width: coinOrigWidth});
                if (thenAnimateIn) animateIn();
            }
        });
    }

    function blink() {
        blinkImg.css({opacity: 1});
        setTimeout(function(){
            blinkImg.css({opacity:0});
        }, 200);
    }
});

