package dev.wisest.shared.library.model.internal;

/*-
 * #%L
 * Code accompanying video course "Learn Spring Boot by Examining 10+ Practical
 *                         Applications"
 * %%
 * Copyright (C) 2025 Juhan Aasaru and Wisest.dev
 * %%
 * The source code (including test code) in this repository is licensed under a
 * Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Licence.
 *
 * Attribution-NonCommercial-NoDerivatives 4.0 International Licence is a standard
 * form licence agreement that does not permit any commercial use or derivatives
 * of the original work. Under this licence: you may only distribute a verbatim
 * copy of the work. If you remix, transform, or build upon the work in any way then
 * you are not allowed to publish nor distribute modified material.
 * You must give appropriate credit and provide a link to the licence.
 *
 * The full licence terms are available from
 * <http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode>
 *
 * All the code (including tests) contained herein should be attributed as:
 * © Juhan Aasaru https://Wisest.dev
 * #L%
 */

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class TextOnPage {
    private final String text;

    private final PDType1Font font;

    private final int fontSize;

    private final PDPage page;

    private final float pageWidth;

    private final float pageHeight;

    private final float textWidth;

    public TextOnPage(PDPage page, String text, PDType1Font font, int fontSize) {
        this.text = text;
        this.fontSize = fontSize;
        this.page = page;
        this.font = font;

        this.pageWidth = page.getMediaBox().getWidth();
        this.pageHeight = page.getMediaBox().getHeight();
        try {
            this.textWidth = (font.getStringWidth(text) / 1000) * fontSize;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public float getPageCenterX() {
        return (pageWidth - textWidth) / 2;
    }

    public float getPageCenterY() {
        return pageHeight / 2;
    }

    public String getText() {
        return text;
    }

    public int getFontSize() {
        return fontSize;
    }

    public PDType1Font getFont() {
        return font;
    }

    public PDPage getPage() {
        return page;
    }

    public float getPageWidth() {
        return pageWidth;
    }

    public float getPageHeight() {
        return pageHeight;
    }

    public float getTextWidth() {
        return textWidth;
    }

}
