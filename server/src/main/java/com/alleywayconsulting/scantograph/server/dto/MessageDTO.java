package com.alleywayconsulting.scantograph.server.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Michael Lake on 2/16/16.
 * Copyright (c) Alleyway Consulting, LLC
 */

public class MessageDTO implements Serializable {

    public Date date;

    public String number;

    public String barcodeContent;

    public MessageDTO(String value, String barcodeContent) {
        this.date = Calendar.getInstance().getTime();
        this.number = value;
        this.barcodeContent = barcodeContent;
    }

}
