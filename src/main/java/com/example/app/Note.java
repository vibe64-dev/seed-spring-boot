package com.example.app;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notes")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = NoteForm.MAX_LENGTH)
	private String body;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	protected Note() {
	}

	public Note(String body, Instant createdAt) {
		this.body = body;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public String getBody() {
		return body;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

}
