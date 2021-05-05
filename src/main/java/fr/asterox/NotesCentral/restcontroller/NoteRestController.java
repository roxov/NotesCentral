package fr.asterox.NotesCentral.restcontroller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.NotesCentral.bean.Note;
import fr.asterox.NotesCentral.service.NoteService;

@RestController
@RequestMapping("rest/note")
public class NoteRestController {

	private static final Logger LOGGER = LogManager.getLogger(NoteRestController.class);

	@Autowired
	private NoteService noteService;

	@PostMapping
	public Note addNote(@RequestBody Note note) {
		LOGGER.info("Adding new note");
		return noteService.addNote(note);
	}

	@GetMapping(value = "/{id}")
	public Note getNoteById(@PathVariable("id") @NotNull(message = "note id is compulsory") Long noteId) {
		LOGGER.info("Getting note identified by id");
		return noteService.findById(noteId);
	}

	@GetMapping(value = "/patient/{id}")
	public List<Note> getAllPatientNotes(
			@PathVariable("id") @NotNull(message = "patientId is compulsory") Long patientId) {
		LOGGER.info("Getting all notes for patient identified by id : " + patientId);
		return noteService.findPatientNotes(patientId);
	}

	@PutMapping(value = "/{id}")
	public Note updateNote(@PathVariable("id") @NotNull(message = "noteId is compulsory") Long noteId,
			@RequestBody Note note) {
		LOGGER.info("Updating note");
		return noteService.updateNote(noteId, note);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteNoteById(@PathVariable("id") @NotNull(message = "note id is compulsory") Long noteId) {
		LOGGER.info("Getting note identified by id : " + noteId);
		noteService.deleteNote(noteId);
	}
}
