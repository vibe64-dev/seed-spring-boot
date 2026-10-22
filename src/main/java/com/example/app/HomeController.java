package com.example.app;

import java.time.Instant;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	private final NoteRepository notes;

	public HomeController(NoteRepository notes) {
		this.notes = notes;
	}

	@GetMapping("/")
	public String home(@ModelAttribute("noteForm") NoteForm noteForm, Model model) {
		model.addAttribute("notes", notes.findTop50ByOrderByIdDesc());
		return "home";
	}

	@PostMapping("/notes")
	public String addNote(@Valid @ModelAttribute("noteForm") NoteForm noteForm, BindingResult result, Model model,
			HttpServletResponse response) {
		if (result.hasErrors()) {
			response.setStatus(HttpStatus.UNPROCESSABLE_CONTENT.value());
			model.addAttribute("notes", notes.findTop50ByOrderByIdDesc());
			return "home";
		}
		notes.save(new Note(noteForm.getBody().strip(), Instant.now()));
		return "redirect:/";
	}

}
