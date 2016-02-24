package com.alleywayconsulting.piggybanker.server.dto;

import java.io.Serializable;

/**
 * Created by Michael Lake on 2/19/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
public class GameDTO implements Serializable {

    public GameDTO(Long sessionId, String status) {
        this.gameId = sessionId;
        this.status = status;
    }

    public String status;

    public Long gameId;
}
