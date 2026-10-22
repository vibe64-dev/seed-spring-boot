package com.example.app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NoteForm {

	public static final int MAX_LENGTH = 500;

	@NotBlank(message = "Write something first.")
	@Size(max = MAX_LENGTH, message = "Keep notes to 500 characters.")
	private String body = "";

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
