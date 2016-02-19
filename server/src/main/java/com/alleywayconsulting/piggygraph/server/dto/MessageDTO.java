package com.alleywayconsulting.piggygraph.server.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Michael Lake on 2/16/16.
 * Copyright (c) Alleyway Consulting, LLC
 */

public class MessageDTO implements Serializable {

    public Date date;

    public String amount;

    public String barcodeContent;

    public MessageDTO(String amount, String barcodeContent) {
        this.date = Calendar.getInstance().getTime();
        this.amount = amount;
        this.barcodeContent = barcodeContent;
    }

}
