package com.example.app;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NoteFormTests {

	private static ValidatorFactory factory;

	private static Validator validator;

	@BeforeAll
	static void createValidator() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	static void closeValidator() {
		factory.close();
	}

	@Test
	void acceptsAShortNote() {
		assertThat(validator.validate(form("Buy milk"))).isEmpty();
	}

	@Test
	void rejectsABlankNote() {
		assertThat(validator.validate(form("   ")))
			.extracting((violation) -> violation.getMessage())
			.containsExactly("Write something first.");
	}

	@Test
	void rejectsANoteLongerThanTheColumn() {
		assertThat(validator.validate(form("x".repeat(NoteForm.MAX_LENGTH + 1))))
			.extracting((violation) -> violation.getMessage())
			.containsExactly("Keep notes to 500 characters.");
	}

	private static NoteForm form(String body) {
		NoteForm form = new NoteForm();
		form.setBody(body);
		return form;
	}

}
