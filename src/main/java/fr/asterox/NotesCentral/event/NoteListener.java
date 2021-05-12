package fr.asterox.NotesCentral.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import fr.asterox.NotesCentral.bean.Note;
import fr.asterox.NotesCentral.service.SequenceGeneratorService;

@Component
public class NoteListener extends AbstractMongoEventListener<Note> {

	private SequenceGeneratorService sequenceGenerator;

	@Autowired
	public NoteListener(SequenceGeneratorService sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Note> event) {
		if (event.getSource().getNoteId() < 1) {
			event.getSource().setNoteId(sequenceGenerator.generateSequence(Note.SEQUENCE_NAME));
		}
	}
}
