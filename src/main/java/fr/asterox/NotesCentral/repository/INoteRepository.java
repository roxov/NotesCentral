package fr.asterox.NotesCentral.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.asterox.NotesCentral.bean.Note;

/**
 * Repository pattern for Note entities.
 *
 */
public interface INoteRepository extends MongoRepository<Note, Long> {
	List<Note> findAllByPatientId(Long patientId);
}
