package com.alleywayconsulting.piggybanker.server.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Michael Lake on 2/19/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
public class Deposit {

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE");

    private Integer amount;

    private Date date;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getDayOfWeek() {
        return dateFormatter.format(date);
    }

    public String getQRCodeValue() {
        StringBuilder sb = new StringBuilder(String.valueOf(amount));
        sb.append("-");
        sb.append(getDayOfWeek());
        return sb.toString();
    }

}
