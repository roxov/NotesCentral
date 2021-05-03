package fr.asterox.NotesCentral.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@DBRef
	private PatientDTO patient;
	private String practitionerNote;

	public Note() {
		super();
	}

	public Note(Long id, PatientDTO patient, String practitionerNote) {
		super();
		this.id = id;
		this.patient = patient;
		this.practitionerNote = practitionerNote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (practitionerNote == null) {
			if (other.practitionerNote != null)
				return false;
		} else if (!practitionerNote.equals(other.practitionerNote))
			return false;
		return true;
	}

}
