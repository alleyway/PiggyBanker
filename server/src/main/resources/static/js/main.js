$(document).ready(function () {
    var container = $("#barcode_container");

    // defined a connection to a new socket endpoint
    var socket = new SockJS('/stomp');

    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        // subscribe to the /topic/message endpoint
        stompClient.subscribe("/topic/message", function (data) {
            var message = JSON.parse(data.body);
            container.empty();
            $("<img/>", {
                src: "data:image/svg+xml;base64," + btoa(message.barcodeContent),
                class: "img-responsive"
            }).appendTo(container);
        });
    });
});