package com.alleywayconsulting.piggygraph.server.service;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.output.svg.SVGCanvasProvider;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created by Michael Lake on 2/16/16.
 * Copyright (c) Alleyway Consulting, LLC
 */

@Service
public class BarcodeServiceImpl implements BarcodeService {


    @Override
    public String createSVGBarcode(String data) throws Exception {


        DataMatrixBean serialBarcode = new DataMatrixBean();

        serialBarcode.setModuleWidth(1.0);
        serialBarcode.setBarHeight(24.0);
        serialBarcode.setFontSize(10.0);
        //serialBarcode.setQuietZone(10.0);
        serialBarcode.setFontName("OCRA");
        serialBarcode.doQuietZone(true);
        serialBarcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);


        SVGCanvasProvider canvas = new SVGCanvasProvider(false, 0);

        serialBarcode.generateBarcode(canvas, data);


        org.w3c.dom.DocumentFragment frag = canvas.getDOMFragment();
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        Source src = new DOMSource(frag);
//        Result res = new StreamResult("code128.svg");
//        trans.transform(src, res);

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        trans.transform(src, result);


        return writer.toString();
    }
}
