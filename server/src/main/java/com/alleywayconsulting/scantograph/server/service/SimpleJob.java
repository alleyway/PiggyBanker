package com.alleywayconsulting.scantograph.server.service;

import com.alleywayconsulting.scantograph.server.dto.MessageDTO;
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


    // this will send a message to an endpoint on which a client can subscribe
    @Scheduled(fixedRate = 3000)
    public void trigger() {
        // sends the message to /topic/message

        int randomNumber = ThreadLocalRandom.current().nextInt(10, 21);

        String timeString = String.valueOf(randomNumber);

        try {

            String barcodeXML = barcodeService.createSVGBarcode(timeString);
            MessageDTO message = new MessageDTO(timeString, barcodeXML);
            template.convertAndSend("/topic/message", message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
