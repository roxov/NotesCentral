package fr.asterox.NotesCentral.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.asterox.NotesCentral.bean.Note;
import fr.asterox.NotesCentral.repository.INoteRepository;
import fr.asterox.NotesCentral.service.NoteService;

@Controller
public class NoteController {

	private static final Logger LOGGER = LogManager.getLogger(NoteController.class);

	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private NoteService noteService;

	// GET LIST OF PATIENT NOTES
	@RequestMapping("/note/list/patient/{id}")
	public String home(@PathVariable("id") @NotNull(message = "noteId is compulsory") Long patientId, Model model) {
		LOGGER.info("Getting the patient notes");
		model.addAttribute("patientId", patientId);
		model.addAttribute("notes", noteRepository.findAllByPatientId(patientId));
		return "note/list";
	}

	// ADD A NOTE
	@GetMapping("/note/patient/{id}/add")
	public String addNewNote(@PathVariable("id") @NotNull(message = "noteId is compulsory") Long patientId, Note note,
			Model model) {
		model.addAttribute("id", patientId);
		model.addAttribute("note", new Note(patientId, ""));
		LOGGER.info("Getting the form to add a note");
		return "note/add";
	}

	@PostMapping("/note/validate")
	public ResponseEntity<Void> validate(@Valid Note note, BindingResult result, Model model) {

		if (result.hasErrors()) {
			LOGGER.error("There are some incorrect datas.");
			ResponseEntity.status(HttpStatus.FOUND)
					.location(URI.create("http://localhost:8080/note/list/patient/" + note.getPatientId())).build();
		}
		LOGGER.info("Adding new note");
		noteService.addViewNote(note);
		LOGGER.info("Redirecting to port 8080");
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(URI.create("http://localhost:8080/note/list/patient/" + note.getPatientId())).build();
	}

	// GET THE NOTE
	@GetMapping("/note/get/{id}")
	public String getNote(@PathVariable("id") Long noteId, Model model) {
		Note note = noteService.findByNoteId(noteId);
		LOGGER.info("Getting the note");
		model.addAttribute("note", note);
		return "note/get";
	}

	// UPDATE THE NOTE
	@GetMapping("/note/update/{id}")
	public String showUpdateForm(@PathVariable("id") @NotNull(message = "noteId is compulsory") Long noteId,
			Model model) {
		Note note = noteService.findByNoteId(noteId);
		LOGGER.info("Getting the form to update a note");
		model.addAttribute("note", note);
		return "note/update";
	}

	@PostMapping("/note/update/{id}")
	public ResponseEntity<Void> updateSelectedNote(@PathVariable("id") Long noteId, @Valid Note note,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			LOGGER.error("There are some incorrect datas.");
			return ResponseEntity.status(HttpStatus.FOUND)
					.location(URI.create("http://localhost:8080/note/list/patient/" + note.getPatientId())).build();
		}
		LOGGER.info("Updating note");
		noteService.updateNote(noteId, note);
		LOGGER.info("Redirecting to port 8080");
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(URI.create("http://localhost:8080/note/list/patient/" + note.getPatientId())).build();
	}

	// DELETE THE NOTE
	@GetMapping("/note/delete/{id}")
	public ResponseEntity<Void> deleteNote(@PathVariable("id") Long noteId, Model model) {
		Long patientId = noteService.findByNoteId(noteId).getPatientId();
		LOGGER.info("Deleting note");
		noteService.deleteNote(noteId);
		LOGGER.info("Redirecting to port 8080");
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(URI.create("http://localhost:8080/note/list/patient/" + patientId)).build();
	}

	@GetMapping("/patient/list")
	public ResponseEntity<Void> getPatientsList() {
		LOGGER.info("Redirecting to port 8080");
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/patient/list"))
				.build();
	}
}
