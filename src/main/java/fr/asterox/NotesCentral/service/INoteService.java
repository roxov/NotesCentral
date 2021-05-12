package fr.asterox.NotesCentral.service;

import java.util.List;

import fr.asterox.NotesCentral.bean.Note;

/**
 * 
 * Microservice managing the notes of patients.
 *
 */
public interface INoteService {
	public Note addNote(Note note);

	public Note findByNoteId(Long noteId);

	public List<Note> findPatientNotes(Long patientId);

	public Note updateNote(Long noteId, Note note);

	public void deleteNote(Long noteId);
}
