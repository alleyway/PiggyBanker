package com.alleywayconsulting.piggybanker.server.service;

import com.alleywayconsulting.piggybanker.server.exceptions.AccountNotFoundException;
import com.alleywayconsulting.piggybanker.server.exceptions.MaxDepositsException;
import com.alleywayconsulting.piggybanker.server.model.Deposit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @Override
    public Deposit getNextDepositForAccount(Long sessionId) throws AccountNotFoundException {

        int randomNumber = ThreadLocalRandom.current().nextInt(0, AccountService.DENOMINATIONS.length);

        ArrayList<Deposit> deposits = ledger.get(sessionId);

        if (deposits == null) throw new AccountNotFoundException();

        Deposit deposit = new Deposit();

        Integer amount = AccountService.DENOMINATIONS[randomNumber];

        deposit.setAmount(amount);

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, deposits.size());

        deposit.setDate(calendar.getTime());

        return deposit;
    }
}
