package dev.wisest.consumerest.model;

/*-
 * #%L
 * "Learn Spring Boot by Examining 10+ Practical Applications" course materials
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


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class course {

    @JsonProperty("courseId")
    private String id;

	private String title;

    @JsonProperty("topic")
	private String courseTopic;

	private Person author;

	protected course() {}
	public course(String id) {
		this.id = id;
	}

	public course(String id, String title, String courseTopic, Person author) {
		this.id = id;
		this.title = title;
		this.courseTopic = courseTopic;
		this.author = author;
	}

	@Override
	public String toString() {
		return String.format(
				"course[id=%s, title='%s', courseTopic='%s' author='%s']",
                id, title, courseTopic, author);
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

    public String getCourseTopic() {
        return courseTopic;
    }

    public Person getAuthor() {
		return author;
	}

}
