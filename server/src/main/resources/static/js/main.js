$(function () {
    var container = $("#barcode_container");

    var amountDiv = $("#amount");

    var coinOrigWidth = $("#coin").width();

    // defined a connection to a new socket endpoint
    var socket = new SockJS('/stomp');

    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        // subscribe to the /topic/message endpoint
        stompClient.subscribe("/topic/message", function (data) {
            var message = JSON.parse(data.body);
            container.empty();
            $("<img/>", {
                src: "data:image/svg+xml;base64," + btoa(message.barcodeContent)
            }).appendTo(container);
            amountDiv[0].innerHTML = "$" + message.amount;
            animateIn();
        });
    });



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
                animateOut();
            }
        });
    }

    function animateOut() {
        var coin = $("#coin");
        var top = 0;

        //ratio of pig hole to pig image width
        var slotWidth = (150/785) * $("#piggy_bank").width() * .9;


        coin.css({top: top, width: coinOrigWidth}).animate({"top": "250px", width: slotWidth}, {
            duration: 700,
            easing: "easeInOutExpo",
            complete: function() {
                $(this).css( {opacity: 0.0, top: 0, width: coinOrigWidth});
            }
        });
    }
});

