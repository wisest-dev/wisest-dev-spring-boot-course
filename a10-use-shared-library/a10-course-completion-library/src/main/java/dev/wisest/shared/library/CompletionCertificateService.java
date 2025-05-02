package dev.wisest.shared.library;

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

import dev.wisest.shared.library.model.CompletionCertificateProperties;
import dev.wisest.shared.library.model.internal.TextOnPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@EnableConfigurationProperties(CompletionCertificateProperties.class)
public class CompletionCertificateService {
    Logger logger = LoggerFactory.getLogger(CompletionCertificateService.class);

    private final CompletionCertificateProperties completionCertificateProperties;

    private final DateTimeFormatter dateFormatter;

    private final PDType1Font textFont;

    private final PDType1Font titleFont;

    public CompletionCertificateService(CompletionCertificateProperties completionCertificateProperties) {
        this.completionCertificateProperties = completionCertificateProperties;
        this.dateFormatter = DateTimeFormatter.ofPattern(completionCertificateProperties.getDateFormat());

        this.textFont = new PDType1Font(Standard14Fonts.FontName.valueOf(completionCertificateProperties.getTextFont().getFace()));
        this.titleFont = new PDType1Font(Standard14Fonts.FontName.valueOf(completionCertificateProperties.getTitleFont().getFace()));
    }

    public String getBrandName() {
        return this.completionCertificateProperties.getBrandName();
    }

    public byte[] getCertificateOfCompletionPdf(String courseName, String studentName, long certificateNumber) throws IOException {
        PDDocument document = new PDDocument();

        PDPage page1 = new PDPage(PDRectangle.A4);
        document.addPage(page1);

        PDPageContentStream page1Content = new PDPageContentStream(document, page1);

        int titleFontSize = completionCertificateProperties.getTitleFont().getSize();
        int textFontSize = completionCertificateProperties.getTextFont().getSize();
        {
            TextOnPage certificateOfCompletionBlock = new TextOnPage(page1, "Certificate of completion", titleFont, titleFontSize);
            page1Content.setFont(certificateOfCompletionBlock.getFont(), certificateOfCompletionBlock.getFontSize());
            page1Content.beginText();
            page1Content.newLineAtOffset(certificateOfCompletionBlock.getPageCenterX(), certificateOfCompletionBlock.getPageCenterY() + 200);
            page1Content.showText(certificateOfCompletionBlock.getText());
            page1Content.endText();
        }

        {
            TextOnPage issuedByBlock = new TextOnPage(page1, "Issued by " + completionCertificateProperties.getBrandName(), textFont, textFontSize);
            page1Content.setFont(issuedByBlock.getFont(), issuedByBlock.getFontSize());
            page1Content.beginText();
            page1Content.newLineAtOffset(issuedByBlock.getPageCenterX(), issuedByBlock.getPageHeight() - 50);
            page1Content.showText(issuedByBlock.getText());
            page1Content.endText();
        }

        {
            TextOnPage courseNameBlock = new TextOnPage(page1, courseName, titleFont, titleFontSize);
            page1Content.setFont(courseNameBlock.getFont(), courseNameBlock.getFontSize());
            page1Content.beginText();
            page1Content.newLineAtOffset(courseNameBlock.getPageCenterX(), courseNameBlock.getPageCenterY() + 50);
            page1Content.showText(courseNameBlock.getText());
            page1Content.endText();
        }

        {
            TextOnPage studentNameBlock = new TextOnPage(page1, "Student: " + studentName, textFont, textFontSize);
            page1Content.setFont(studentNameBlock.getFont(), studentNameBlock.getFontSize());
            page1Content.beginText();
            page1Content.newLineAtOffset(50, studentNameBlock.getPageCenterY() - 80);
            page1Content.showText(studentNameBlock.getText());
            page1Content.endText();
        }

        {
            String formattedDate = LocalDate.now().format(dateFormatter);

            TextOnPage completionDateBlock = new TextOnPage(page1, "Date: " + formattedDate, textFont, textFontSize);
            page1Content.setFont(completionDateBlock.getFont(), completionDateBlock.getFontSize());
            page1Content.beginText();
            page1Content.newLineAtOffset(50, completionDateBlock.getPageCenterY() - 120);
            page1Content.showText(completionDateBlock.getText());
            page1Content.endText();
        }

        {
            TextOnPage certificateIdBlock = new TextOnPage(page1, "Certificate number #" + certificateNumber, textFont, textFontSize);
            page1Content.setFont(certificateIdBlock.getFont(), certificateIdBlock.getFontSize());
            page1Content.beginText();
            page1Content.newLineAtOffset(certificateIdBlock.getPageCenterX(), 50);
            page1Content.showText(certificateIdBlock.getText());
            page1Content.endText();
        }

        page1Content.close();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        logger.debug("Saved bytes to PDF");

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

}
