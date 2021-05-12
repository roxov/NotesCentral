package fr.asterox.NotesCentral.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.asterox.NotesCentral.bean.Note;

/**
 * Repository pattern for Note entities.
 *
 */
public interface INoteRepository extends MongoRepository<Note, Long> {
	Optional<Note> findByNoteId(Long noteId);

	List<Note> findAllByPatientId(Long patientId);

	void deleteByNoteId(Long noteId);
}
