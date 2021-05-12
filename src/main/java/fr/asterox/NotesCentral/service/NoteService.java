package fr.asterox.NotesCentral.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import fr.asterox.NotesCentral.bean.Note;
import fr.asterox.NotesCentral.proxy.PatientManagementProxy;
import fr.asterox.NotesCentral.repository.INoteRepository;
import fr.asterox.NotesCentral.util.NoNoteException;
import fr.asterox.NotesCentral.util.NoPatientException;

@Service
public class NoteService implements INoteService {
	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private PatientManagementProxy patientManagementProxy;

	@Autowired
	private SequenceGeneratorService sequenceGenerator;

	private static final Logger LOGGER = LogManager.getLogger(NoteService.class);

	@Override
	public Note addNote(Note note) {
		if (!patientManagementProxy.askExistenceOfPatient(note.getPatientId())) {
			LOGGER.info("The requested patient does not exist.");
			throw new NoPatientException("This patient does not exist.");
		}

		LOGGER.info("Adding new note to patient with id : " + note.getPatientId());
		note.setNoteId(sequenceGenerator.generateSequence(Note.SEQUENCE_NAME));
		return noteRepository.save(note);
	}

	public Note addViewNote(Note note) {
		LOGGER.info("Adding new note to patient with id : " + note.getPatientId());
		note.setNoteId(sequenceGenerator.generateSequence(Note.SEQUENCE_NAME));
		return noteRepository.save(note);
	}

	@Override
	public Note findByNoteId(Long noteId) {
		Optional<Note> note = noteRepository.findByNoteId(noteId);

		if (note.isEmpty()) {
			LOGGER.info("The requested note does not exist.");
			throw new NoNoteException("This note does not exist.");
		}

		LOGGER.info("Getting note");
		return note.get();
	}

	@Override
	public List<Note> findPatientNotes(Long patientId) {
		List<Note> patientNotes = noteRepository.findAllByPatientId(patientId);

		if (patientNotes.isEmpty()) {
			LOGGER.info("This patient has no note.");
			throw new NoNoteException("This patient has no note.");
		}

		LOGGER.info("Getting patient notes");
		return patientNotes;
	}

	public String getPatientStringNotes(
			@PathVariable("id") @NotNull(message = "patientId is compulsory") Long patientId) {
		LOGGER.info("Getting all notes in a String for patient identified by id : " + patientId);
		return this.findPatientNotes(patientId).stream().map(n -> n.getPractitionerNote()).reduce(String::concat)
				.orElse("");
	}

	@Override
	public Note updateNote(Long noteId, Note note) {
		Optional<Note> searchedNote = noteRepository.findByNoteId(noteId);

		if (searchedNote.isEmpty()) {
			LOGGER.info("The requested note does not exist.");
			throw new NoNoteException("This note does not exist.");
		}

		if (!patientManagementProxy.askExistenceOfPatient(note.getPatientId())) {
			LOGGER.info("The requested patient does not exist.");
			throw new NoPatientException("This patient does not exist.");
		}

		LOGGER.info("Updating note");
		searchedNote.get().setPatientId(note.getPatientId());
		searchedNote.get().setPractitionerNote(note.getPractitionerNote());
		return noteRepository.save(searchedNote.get());
	}

	@Override
	public void deleteNote(Long noteId) {
		Optional<Note> note = noteRepository.findByNoteId(noteId);

		if (note.isEmpty()) {
			LOGGER.info("The requested note does not exist.");
			throw new NoNoteException("This note does not exist.");
		}

		LOGGER.info("Deleting note");
		noteRepository.deleteByNoteId(noteId);
	}

	public INoteRepository setNoteRepository(INoteRepository noteRepository) {
		return this.noteRepository = noteRepository;
	}

	public PatientManagementProxy setPatientManagementProxy(PatientManagementProxy patientManagementProxy) {
		return this.patientManagementProxy = patientManagementProxy;
	}

	public SequenceGeneratorService setSequenceGeneratorService(SequenceGeneratorService sequenceGeneratorService) {
		return this.sequenceGenerator = sequenceGeneratorService;
	}

}
