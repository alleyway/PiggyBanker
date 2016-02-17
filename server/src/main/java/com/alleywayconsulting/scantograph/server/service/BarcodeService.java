package com.alleywayconsulting.scantograph.server.service;

/**
 * Created by Michael Lake on 2/16/16.
 * Copyright (c) Alleyway Consulting, LLC
 */

public interface BarcodeService {


    String createSVGBarcode(String data) throws Exception;
}
