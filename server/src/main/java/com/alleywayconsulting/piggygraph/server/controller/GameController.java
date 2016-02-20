package com.alleywayconsulting.piggygraph.server.controller;

import com.alleywayconsulting.piggygraph.server.dto.GameDTO;
import com.alleywayconsulting.piggygraph.server.service.BarcodeService;
import com.alleywayconsulting.piggygraph.server.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * Created by Michael Lake on 2/19/16.
 * Copyright (c) Alleyway Consulting, LLC
 */

@RestController
@RequestMapping("/api/game")
public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    @Autowired
    BarcodeService barcodeService;

    @Autowired
    AccountService gameService;

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/start/{sessionId}", method = RequestMethod.GET)
    public void start(HttpServletResponse response, @PathVariable Long sessionId) {
        LOG.info("Start game");

        GameDTO gameDTO = new GameDTO(sessionId);

        gameService.createAccount(sessionId);

        template.convertAndSend("/topic/message", gameDTO);
    }

    @RequestMapping(value = "/startcode/{sessionId}", method = RequestMethod.GET, produces = "image/svg+xml")
    public void startCode(HttpServletResponse response, @PathVariable String sessionId) {
        LOG.info("Creating new game barcode");
        try {
            response.setHeader("Content-Type", "image/svg+xml");

            StringBuffer sb = new StringBuffer();
            sb.append("http://");
            sb.append(InetAddress.getLocalHost().getHostAddress());
            sb.append(":8080/api/game/start/");
            sb.append(sessionId);

            String code = barcodeService.createSVGBarcode(sb.toString());

            FileCopyUtils.copy(code.getBytes(), response.getOutputStream());
        } catch (Exception e) {
            LOG.error("Unable to create SGV", e);
        }
    }
}
