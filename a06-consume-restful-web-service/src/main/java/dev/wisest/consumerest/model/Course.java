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


public class Course {

	private String courseId;

	private String title;

	//private CourseTopic topic;

	private Person author;

	protected Course() {}
	public Course(String courseId) {
		this.courseId = courseId;
	}

	public Course(String courseId, String title, CourseTopic topic, Person author) {
		this.courseId = courseId;
		this.title = title;
		//this.topic = topic;
		this.author = author;
	}

	@Override
	public String toString() {
		return String.format(
				"Course[id=%s, title='%s', author='%s']",
				courseId, title, author);
	}

	public String getCourseId() {
		return courseId;
	}

	public String getTitle() {
		return title;
	}


	public Person getAuthor() {
		return author;
	}

}
