package com.alleywayconsulting.piggybanker.server.service;

import com.alleywayconsulting.piggybanker.server.exceptions.AccountNotFoundException;
import com.alleywayconsulting.piggybanker.server.exceptions.MaxDepositsException;
import com.alleywayconsulting.piggybanker.server.model.Deposit;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Michael Lake on 2/19/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
public interface AccountService {

    Integer[] DENOMINATIONS = new Integer[]{1, 5, 10, 20, 25, 50, 75};

    Integer MAX_DEPOSITS = 7;

    void createAccount(Long sessionId);

    ConcurrentHashMap<Long, ArrayList<Deposit>> getLedger();

    Deposit depositToAccount(Long sessionId, Deposit deposit) throws AccountNotFoundException, MaxDepositsException;

    Deposit getNextDepositForAccount(Long sessionId) throws AccountNotFoundException;
}
