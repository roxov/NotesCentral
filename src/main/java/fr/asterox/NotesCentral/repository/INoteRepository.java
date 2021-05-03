package fr.asterox.NotesCentral.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.asterox.NotesCentral.bean.Note;

/**
 * Repository pattern for Patient entities.
 *
 */
public interface INoteRepository extends MongoRepository<Note, Long> {
	Optional<Note> findByPatientId(Long patientId);
}
