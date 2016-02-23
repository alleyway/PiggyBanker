package com.alleywayconsulting.piggygraph.server.service;

import com.alleywayconsulting.piggygraph.server.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Michael Lake on 2/16/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
@Component
public class SimpleJob {


    @Autowired
    private SimpMessagingTemplate template;

    // this will send a message to an endpoint on which a client can subscribe
    @Scheduled(fixedRate = 4000)
    public void trigger() throws Exception {

        GameDTO gameDTO = new GameDTO(null, "HEARTBEAT");

        template.convertAndSend("/topic/message", gameDTO);

    }

}
