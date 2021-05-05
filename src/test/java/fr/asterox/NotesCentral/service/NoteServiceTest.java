package fr.asterox.NotesCentral.service;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.asterox.NotesCentral.bean.Note;
import fr.asterox.NotesCentral.proxy.PatientManagementProxy;
import fr.asterox.NotesCentral.repository.INoteRepository;
import fr.asterox.NotesCentral.util.NoNoteException;
import fr.asterox.NotesCentral.util.NoPatientException;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
	@Mock
	private INoteRepository noteRepository;

	@Mock
	private PatientManagementProxy patientManagementProxy;

	@InjectMocks
	NoteService noteService;

	@BeforeEach
	public void setUp() {
		noteService = new NoteService();
		noteService.setNoteRepository(noteRepository);
	}

	@Test
	public void givenANote_whenAddNote_thenReturnCreatedNote() {
		// GIVEN
		Note note = new Note(1L, 2L, "Ceci est une nouvelle note");
		when(noteRepository.save(note)).thenReturn(note);

		// WHEN
		Note result = noteService.addNote(note);

		// THEN
		verify(noteRepository, Mockito.times(1)).save(any(Note.class));
		assertEquals(1L, result.getNoteId());
		assertEquals(2L, result.getPatientId());
		assertEquals("Ceci est une nouvelle note", result.getPractitionerNote());
	}

	@Test
	public void givenANoteForInexistentPatient_whenAddNote_thenThrowNoPatientException() {
		// GIVEN
		noteService.setPatientManagementProxy(patientManagementProxy);
		Note note = new Note(1L, 2L, "Ceci est une nouvelle note");
		when(patientManagementProxy.askExistenceOfPatient(2L)).thenReturn(false);

		// WHEN
		Exception exception = assertThrows(NoPatientException.class, () -> {
			noteService.addNote(note);
		});

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).askExistenceOfPatient(2L);
		assertEquals(exception.getMessage(), "This patient does not exist.");
	}

	@Test
	public void givenANoteId_whenGetNote_thenReturnTheNote() {
		// GIVEN
		Note note = new Note(1L, 2L, "Ceci est une note existante");
		when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

		// WHEN
		Note result = noteService.findById(1L);

		// THEN
		verify(noteRepository, Mockito.times(1)).findById(1L);
		assertEquals(1L, result.getNoteId());
		assertEquals(2L, result.getPatientId());
		assertEquals("Ceci est une note existante", result.getPractitionerNote());
	}

	@Test
	public void givenANonexistentNoteId_whenGetNote_thenThrowNoNoteException() {
		// GIVEN
		when(noteRepository.findById(1L)).thenReturn(Optional.empty());

		// WHEN
		Exception exception = assertThrows(NoNoteException.class, () -> {
			noteService.findById(1L);
		});

		// THEN
		verify(noteRepository, Mockito.times(1)).findById(1L);
		assertEquals(exception.getMessage(), "This note does not exist.");
	}

	@Test
	public void givenTwoNotesForAPatient_whenGetPatientNotes_thenReturnListOfTwoNotes() {
		// GIVEN
		Note note1 = new Note(1L, 2L, "Ceci est la première note");
		Note note2 = new Note(2L, 2L, "Ceci est la deuxième note");
		List<Note> patientNotes = List.of(note1, note2);
		when(noteRepository.findAllByPatientId(2L)).thenReturn(patientNotes);

		// WHEN
		List<Note> result = noteService.findPatientNotes(2L);

		// THEN
		verify(noteRepository, Mockito.times(1)).findAllByPatientId(2L);
		assertEquals(2, result.size());
		assertEquals(1L, result.get(0).getNoteId());
		assertEquals("Ceci est la deuxième note", result.get(1).getPractitionerNote());
	}

	@Test
	public void givenNoNoteForAPatient_whenGetPatientNotes_thenThrowNoNoteException() {
		// GIVEN
		when(noteRepository.findAllByPatientId(2L)).thenReturn(emptyList());

		// WHEN
		Exception exception = assertThrows(NoNoteException.class, () -> {
			noteService.findPatientNotes(2L);
		});

		// THEN
		verify(noteRepository, Mockito.times(1)).findAllByPatientId(2L);
		assertEquals(exception.getMessage(), "This patient has no note.");
	}

	@Test
	public void givenANote_whenUpdateNote_thenReturnUpdatedNote() {
		// GIVEN
		Note note = new Note(1L, 2L, "Ceci est la note modifiée");
		when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
		when(noteRepository.save(note)).thenReturn(note);

		// WHEN
		Note updatedNote = noteService.updateNote(1L, note);

		// THEN
		verify(noteRepository, Mockito.times(1)).findById(any(Long.class));
		verify(noteRepository, Mockito.times(1)).save(any(Note.class));
		assertEquals(1L, updatedNote.getNoteId());
		assertEquals(2L, updatedNote.getPatientId());
		assertEquals("Ceci est la note modifiée", updatedNote.getPractitionerNote());
	}

	@Test
	public void givenNonexistentNote_whenUpdateNote_thenThrowNoNoteException() {
		// GIVEN
		Note note = new Note(1L, 2L, "Ceci est la note modifiée");
		when(noteRepository.findById(1L)).thenReturn(Optional.empty());

		// WHEN
		Exception exception = assertThrows(NoNoteException.class, () -> {
			noteService.updateNote(1L, note);
		});

		// THEN
		verify(noteRepository, Mockito.times(1)).findById(1L);
		assertEquals(exception.getMessage(), "This note does not exist.");
	}

	@Test
	public void givenANote_whenDeleteNote_thenVerifyMethodCalled() {
		// GIVEN
		Note note = new Note(1L, 2L, "Ceci est la note à supprimer");
		when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
		doNothing().when(noteRepository).deleteById(1L);

		// WHEN
		noteService.deleteNote(1L);

		// THEN
		verify(noteRepository, Mockito.times(1)).findById(any(Long.class));
		verify(noteRepository, Mockito.times(1)).deleteById(any(Long.class));
	}

	@Test
	public void givenANonexistentNote_whenDeleteNote_thenThrowNoNoteException() {
		// GIVEN
		when(noteRepository.findById(1L)).thenReturn(Optional.empty());

		// WHEN
		Exception exception = assertThrows(NoNoteException.class, () -> {
			noteService.deleteNote(1L);
		});

		// THEN
		verify(noteRepository, Mockito.times(1)).findById(1L);
		assertEquals(exception.getMessage(), "This note does not exist.");
	}

}
