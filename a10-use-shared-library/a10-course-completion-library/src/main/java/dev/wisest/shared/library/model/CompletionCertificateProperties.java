package dev.wisest.shared.library.model;

/*-
 * #%L
 * Code accompanying course "Learn Spring Boot by Examining 10+ Practical
 *                         Applications"
 * %%
 * Copyright (C) 2025 Juhan Aasaru and Wisest.dev
 * %%
 * The source code (including test code) in this repository is licensed under a
 * Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * %
 * Attribution-NonCommercial-NoDerivatives 4.0 International License is a standard
 * form license agreement that does not permit any commercial use or derivatives
 * of the original work. Under this license: you may only distribute a verbatim
 * copy of the work. If you remix, transform, or build upon the work in any way then
 * you are not allowed to publish nor distribute modified material.
 * You must give appropriate credit and provide a link to the license.
 * %
 * The full license terms are available from
 * <http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode>
 * %
 * All the code (including tests) contained herein should be attributed as:
 * (Copyright) Juhan Aasaru https://Wisest.dev
 * #L%
 */

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("certificate-of-completion.pdf-properties")
public class CompletionCertificateProperties {

    @NotNull
    @Size(min = 1, max = 20, message = "BrandName should not exceed {max} characters")
    private String brandName;

    @Valid
    private FontDetails titleFont;

    @Valid
    private FontDetails textFont;

    @Pattern(regexp = "[dDmMyY.-]+")
    private String dateFormat;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public FontDetails getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(FontDetails titleFont) {
        this.titleFont = titleFont;
    }

    public FontDetails getTextFont() {
        return textFont;
    }

    public void setTextFont(FontDetails textFont) {
        this.textFont = textFont;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

}
