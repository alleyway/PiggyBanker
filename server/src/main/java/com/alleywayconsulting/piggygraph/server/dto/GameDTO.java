package com.alleywayconsulting.piggygraph.server.dto;

import java.io.Serializable;

/**
 * Created by Michael Lake on 2/19/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
public class GameDTO implements Serializable {

    public GameDTO(Long sessionId) {
        this.gameId = sessionId;
    }

    public Long gameId;
}
