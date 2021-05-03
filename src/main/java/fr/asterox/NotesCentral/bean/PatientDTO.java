package fr.asterox.NotesCentral.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "patient")
public class PatientDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PATIENT_ID")
	private Long patientId;
	@Column(name = "FIRST_NAME")
	private String givenName;
	@Column(name = "LAST_NAME")
	private String familyName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthdate;
	private String address;
	private String phone;
	@Enumerated(EnumType.STRING)
	private Sex sex;

	public enum Sex {
		M("M"), F("F");

		private String name;

		Sex(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	};

	public PatientDTO() {
		super();
	}

	public PatientDTO(String givenName, String familyName, String address, String phone) {
		super();
		this.givenName = givenName;
		this.familyName = familyName;
		this.address = address;
		this.phone = phone;
	}

	public PatientDTO(String givenName, String familyName, Date birthdate, String address, String phone, Sex sex) {
		super();
		this.givenName = givenName;
		this.familyName = familyName;
		this.birthdate = birthdate;
		this.address = address;
		this.phone = phone;
		this.sex = sex;
	}

	public PatientDTO(Long patientId, String givenName, String familyName, Date birthdate, String address, String phone,
			Sex sex) {
		super();
		this.patientId = patientId;
		this.givenName = givenName;
		this.familyName = familyName;
		this.birthdate = birthdate;
		this.address = address;
		this.phone = phone;
		this.sex = sex;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
		result = prime * result + ((familyName == null) ? 0 : familyName.hashCode());
		result = prime * result + ((givenName == null) ? 0 : givenName.hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
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
		PatientDTO other = (PatientDTO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (birthdate == null) {
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (familyName == null) {
			if (other.familyName != null)
				return false;
		} else if (!familyName.equals(other.familyName))
			return false;
		if (givenName == null) {
			if (other.givenName != null)
				return false;
		} else if (!givenName.equals(other.givenName))
			return false;
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else if (!patientId.equals(other.patientId))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (sex != other.sex)
			return false;
		return true;
	}

}
