package com.alleywayconsulting.piggygraph.server.service;

import com.alleywayconsulting.piggygraph.server.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Michael Lake on 2/16/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
@Component
public class SimpleJob {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private BarcodeService barcodeService;

    private String[] denominations = new String[] {"1", "5", "10", "20", "25", "50", "75", "100"};


    // this will send a message to an endpoint on which a client can subscribe
    @Scheduled(fixedRate = 5000)
    public void trigger() {
        // sends the message to /topic/message

        int randomNumber = ThreadLocalRandom.current().nextInt(0, denominations.length);

        String amount = denominations[randomNumber];

        try {

            String barcodeXML = barcodeService.createSVGBarcode(amount);
            MessageDTO message = new MessageDTO(amount, barcodeXML);
            template.convertAndSend("/topic/message", message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
