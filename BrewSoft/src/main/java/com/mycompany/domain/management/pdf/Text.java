package com.mycompany.domain.management.pdf;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class Text {

    public Text() {

    }

    public PDPageContentStream createText(
            PDPageContentStream contentStream,
            PDFont pDFont, float fontSize,
            float offsetX, float offSetY,
            String text) throws IOException {

        contentStream.beginText();
        contentStream.setFont(pDFont, fontSize);
        contentStream.newLineAtOffset(offsetX, offSetY);
        contentStream.showText(text);
        contentStream.endText();

        return contentStream;
    }
}
