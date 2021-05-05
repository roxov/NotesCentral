package fr.asterox.NotesCentral.service;

import java.util.List;

import fr.asterox.NotesCentral.bean.Note;

public interface INoteService {
	public Note addNote(Note note);

	public Note findById(Long noteId);

	public List<Note> findPatientNotes(Long patientId);

	public Note updateNote(Long noteId, Note note);

	public void deleteNote(Long noteId);
}
