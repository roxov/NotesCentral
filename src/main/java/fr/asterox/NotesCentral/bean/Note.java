package fr.asterox.NotesCentral.bean;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 100)
public class Note {
	@Transient
	public static final String SEQUENCE_NAME = "notes_sequence";

	private ObjectId _id;
	@Id
	private long noteId;
	private Long patientId;
	private String practitionerNote;

	public Note() {
		super();
	}

	public Note(Long patientId, String practitionerNote) {
		super();
		this.patientId = patientId;
		this.practitionerNote = practitionerNote;
	}

	public Note(String practitionerNote) {
		super();
		this.practitionerNote = practitionerNote;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public long getNoteId() {
		return noteId;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getPractitionerNote() {
		return practitionerNote;
	}

	public void setPractitionerNote(String practitionerNote) {
		this.practitionerNote = practitionerNote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + (int) (noteId ^ (noteId >>> 32));
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
		result = prime * result + ((practitionerNote == null) ? 0 : practitionerNote.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (noteId != other.noteId)
			return false;
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else if (!patientId.equals(other.patientId))
			return false;
		if (practitionerNote == null) {
			if (other.practitionerNote != null)
				return false;
		} else if (!practitionerNote.equals(other.practitionerNote))
			return false;
		return true;
	}

}
