package com.alleywayconsulting.piggygraph.server.service;

import com.alleywayconsulting.piggygraph.server.dto.CoinDTO;
import com.alleywayconsulting.piggygraph.server.model.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Michael Lake on 2/16/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
@Component
public class SimpleJob {

    private static final int MAX_DEPOSITS = 7;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private BarcodeService barcodeService;

    @Autowired
    private AccountService accountService;

    private Integer[] denominations = new Integer[]{1, 5, 10, 20, 25, 50, 75, 100};


    // this will send a message to an endpoint on which a client can subscribe
    @Scheduled(fixedRate = 5000)
    public void trigger() throws Exception {
        // sends the message to /topic/message



        ConcurrentHashMap<Long, ArrayList<Deposit>> ledger = accountService.getLedger();

        for (Long sessionId : ledger.keySet()) {
            if (ledger.get(sessionId).size() < MAX_DEPOSITS) {

                int randomNumber = ThreadLocalRandom.current().nextInt(0, denominations.length);

                Integer amount = denominations[randomNumber];

                Deposit deposit = new Deposit();
                deposit.setAmount(amount);
                ledger.get(sessionId).add(deposit);
                String barcodeXML = barcodeService.createSVGBarcode(String.valueOf(amount));

                CoinDTO coin = new CoinDTO(sessionId, amount, barcodeXML);

                template.convertAndSend("/topic/message", coin);

            }
        }


    }

}
