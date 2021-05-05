package fr.asterox.NotesCentral.bean;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
public class Note {
	@Id
	private Long noteId;
	private Long patientId;
	private String practitionerNote;

	public Note() {
		super();
	}

	public Note(Long noteId, Long patientId, String practitionerNote) {
		super();
		this.noteId = noteId;
		this.patientId = patientId;
		this.practitionerNote = practitionerNote;
	}

	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
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
		result = prime * result + ((noteId == null) ? 0 : noteId.hashCode());
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
		if (noteId == null) {
			if (other.noteId != null)
				return false;
		} else if (!noteId.equals(other.noteId))
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
