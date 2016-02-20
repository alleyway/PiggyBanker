package com.alleywayconsulting.piggygraph.server.service;

import com.alleywayconsulting.piggygraph.server.model.Deposit;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Michael Lake on 2/19/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
public interface AccountService {

    void createAccount(Long sessionId);

    ConcurrentHashMap<Long, ArrayList<Deposit>> getLedger();
}
