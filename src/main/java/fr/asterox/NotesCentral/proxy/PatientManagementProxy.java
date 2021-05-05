package fr.asterox.NotesCentral.proxy;

import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PatientManagement", url = "localhost:8081")
public interface PatientManagementProxy {

	@GetMapping(value = "rest/patient/exist/{id}")
	public boolean askExistenceOfPatient(
			@PathVariable("id") @NotNull(message = "patientId is compulsory") Long patientId);
}
