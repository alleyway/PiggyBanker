package com.alleywayconsulting.piggybanker.server.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.EnumMap;

/**
 * Created by Michael Lake on 2/16/16.
 * Copyright (c) Alleyway Consulting, LLC
 */

@Service
public class BarcodeServiceImpl implements BarcodeService {

    private static SVGGraphics2D toSvgDocument(BitMatrix matrix, MatrixToImageConfig config) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        SVGGraphics2D svgGraphics = new SVGGraphics2D(width, height);

        // just make it transparent
//        svgGraphics.setColor(new Color(config.getPixelOffColor()));
//        svgGraphics.fillRect(0, 0, width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (matrix.get(x, y)) {
                    svgGraphics.setColor(new Color(config.getPixelOnColor()));
                    svgGraphics.fillRect(x, y, 1, 1);
                }
            }
        }

        return svgGraphics;
    }

    @Override
    public String createSVGBarcode(String data) throws Exception {

        QRCodeWriter writer = new QRCodeWriter();

        EnumMap<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 96, 96, hints);

        SVGGraphics2D svgDocument = toSvgDocument(bitMatrix, new MatrixToImageConfig());

        return svgDocument.getSVGDocument();
    }
}
