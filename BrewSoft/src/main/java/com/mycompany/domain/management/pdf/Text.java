package com.mycompany.domain.management.pdf;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class Text {

    public Text() {
    }

    /**
     * Used to generate text with a specified text, font of type PDFont, font
     * size, and the offset x and y on the page. Returns a contentStream to
     * which more text can be added with different position or font and so on.
     * Reference of usage to
     * {@link PDF#addPageWithBatchInfo(com.mycompany.crossCutting.objects.BatchReport, java.util.Map)}
     *
     * @param contentStream, type PDPageContentStream
     * @param pDFont, type PDFont
     * @param fontSize, type float
     * @param offsetX, type float
     * @param offSetY, type float
     * @param text, type String
     *
     * @return returns a PDPageContentStream of the specified text, fontSize,
     * fontType (PDFfont) and x and y offset.
     *
     * @throws IOException, if an error occured while writing to the
     * contentStream, or if the PDPageContentStream is not instantiated.
     */
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
