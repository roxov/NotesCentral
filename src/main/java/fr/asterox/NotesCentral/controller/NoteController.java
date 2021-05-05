package fr.asterox.NotesCentral.controller;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.asterox.NotesCentral.repository.INoteRepository;
import fr.asterox.NotesCentral.service.NoteService;

@Controller
public class NoteController {

	private static final Logger LOGGER = LogManager.getLogger(NoteController.class);

	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private NoteService noteService;

	// GET LIST OF PATIENT NOTES

	// ADD A NOTE

	// GET THE NOTE

	// UPDATE THE NOTE

	// DELETE THE NOTE

	@GetMapping("/note/delete/{id}")
	public ResponseEntity<Void> deleteNote(@PathVariable("id") Long noteId, Model model) {
		LOGGER.info("Deleting note");
		noteService.deleteNote(noteId);
		LOGGER.info("Redirecting to port 8080");
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/note</list")).build();
	}

//	@RequestMapping("/patient/list")
//	public String home(Model model) {
//		LOGGER.info("Getting the patients list");
//		model.addAttribute("patients", patientRepository.findAll());
//		return "patient/list";
//	}
//
//	@GetMapping("/patient/add")
//	public String addNewPatient(Patient patient) {
//		LOGGER.info("Getting the form to add a patient");
//		return "patient/add";
//	}
//
//	@PostMapping("/patient/validate")
//	public ResponseEntity<Void> validate(@Valid Patient patient, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			LOGGER.error("There are some incorrect datas.");
//			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/patient/list"))
//					.build();
//		}
//		LOGGER.info("Adding new patient");
//		patientService.addPatient(patient);
//		LOGGER.info("Redirecting to port 8080");
//		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/patient/list"))
//				.build();
//	}
//
//	@GetMapping("/patient/get/{id}")
//	public String getPatient(@PathVariable("id") Long patientId, Model model) {
//		Patient patient = patientService.findById(patientId);
//		LOGGER.info("Getting the form to get demographic information of patient");
//		model.addAttribute("patient", patient);
//		return "patient/get";
//	}
//
//	@GetMapping("/patient/update/{id}")
//	public String showUpdateForm(@PathVariable("id") @NotNull(message = "patientId is compulsory") Long patientId,
//			Model model) {
//		Patient patient = patientService.findById(patientId);
//		LOGGER.info("Getting the form to update a patient");
//		model.addAttribute("patient", patient);
//		return "patient/update";
//	}
//
//	@PostMapping("/patient/update/{id}")
//	public ResponseEntity<Void> updateSelectedPatient(@PathVariable("id") Long patientId, @Valid Patient patient,
//			BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			LOGGER.error("There are some incorrect datas.");
//			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/patient/list"))
//					.build();
//		}
//		LOGGER.info("Updating patient");
//		patientService.updatePatient(patientId, patient);
//		LOGGER.info("Redirecting to port 8080");
//		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/patient/list"))
//				.build();
//	}
//
//	@GetMapping("/patient/delete/{id}")
//	public ResponseEntity<Void> deletePatient(@PathVariable("id") Long patientId, Model model) {
//		LOGGER.info("Deleting patient");
//		patientService.deletePatient(patientId);
//		LOGGER.info("Redirecting to port 8080");
//		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/patient/list"))
//				.build();
//	}

}
