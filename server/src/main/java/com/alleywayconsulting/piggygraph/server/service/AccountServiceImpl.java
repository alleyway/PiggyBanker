package com.alleywayconsulting.piggygraph.server.service;

import com.alleywayconsulting.piggygraph.server.exceptions.AccountNotFoundException;
import com.alleywayconsulting.piggygraph.server.exceptions.MaxDepositsException;
import com.alleywayconsulting.piggygraph.server.model.Deposit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Michael Lake on 2/19/16.
 * Copyright (c) Alleyway Consulting, LLC
 */

@Service
public class AccountServiceImpl implements AccountService {

    private ConcurrentHashMap<Long, ArrayList<Deposit>> ledger;

    public AccountServiceImpl() {
        this.ledger = new ConcurrentHashMap<Long, ArrayList<Deposit>>();
    }

    @Override
    public void createAccount(Long sessionId) {

        if (ledger.containsKey(sessionId)) {
            ledger.remove(sessionId);
        }
        ledger.put(sessionId, new ArrayList<Deposit>());
    }

    @Override
    public ConcurrentHashMap<Long, ArrayList<Deposit>> getLedger() {
        return ledger;
    }

    @Override
    public Deposit depositToAccount(Long sessionId, Deposit deposit) throws AccountNotFoundException, MaxDepositsException {

        ArrayList<Deposit> deposits = ledger.get(sessionId);

        if (deposits == null) throw new AccountNotFoundException();

        if (deposits.size() < MAX_DEPOSITS) {
            deposits.add(deposit);
            return deposit;

        } else {
            throw new MaxDepositsException();
        }
    }
}
