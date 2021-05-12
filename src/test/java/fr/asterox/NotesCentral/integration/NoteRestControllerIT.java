package fr.asterox.NotesCentral.integration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import fr.asterox.NotesCentral.bean.Note;
import fr.asterox.NotesCentral.proxy.PatientManagementProxy;
import fr.asterox.NotesCentral.repository.INoteRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteRestControllerIT {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	INoteRepository noteRepository;

	@MockBean
	PatientManagementProxy patientManagementProxy;

	@BeforeEach
	void setUp() {
		noteRepository.deleteAll();
	}

	@WithMockUser
	@Test
	void givenANote_whenPostNote_thenReturns200AndNote() throws Exception {
		when(patientManagementProxy.askExistenceOfPatient(2L)).thenReturn(true);
		Note note = new Note(2L, "Ceci est une nouvelle note");
		mockMvc.perform(post("/rest/note").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(note))).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.patientId").value(2L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.practitionerNote").value("Ceci est une nouvelle note"));
		verify(patientManagementProxy, Mockito.times(1)).askExistenceOfPatient(2L);
	}

	@WithMockUser
	@Test
	public void givenANoteId_whenGetNote_thenReturnOkAndNote() throws Exception {
		// GIVEN
		when(patientManagementProxy.askExistenceOfPatient(2L)).thenReturn(true);
		Note note = new Note(2L, "Ceci est une note existante");
		String jsonResponse = mockMvc.perform(post("/rest/note").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(note))).andReturn().getResponse().getContentAsString();
		Long noteId = ((Integer) JsonPath.parse(jsonResponse).read("$.noteId")).longValue();

		// WHEN THEN
		mockMvc.perform(MockMvcRequestBuilders.get("/rest/note/{id}", noteId).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.noteId").value(noteId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.practitionerNote").value("Ceci est une note existante"));
	}

	@WithMockUser
	@Test
	public void givenIdForAPatientWithTwoNotes_whenGetPatientNotes_thenReturnOkAndListOfNotes() throws Exception {
		// GIVEN
		when(patientManagementProxy.askExistenceOfPatient(2L)).thenReturn(true);
		Note note1 = new Note(2L, "Ceci est la première note");
		mockMvc.perform(post("/rest/note").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(note1))).andReturn().getResponse().getContentAsString();

		Note note2 = new Note(2L, "Ceci est la deuxième note");
		mockMvc.perform(post("/rest/note").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(note2))).andReturn().getResponse().getContentAsString();

		// WHEN THEN
		mockMvc.perform(
				MockMvcRequestBuilders.get("/rest/note/list/patient/{id}", 2).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
	}

	@WithMockUser
	@Test
	void givenANote_whenPutNote_thenReturns200AndUpdatedNote() throws Exception {
		// GIVEN
		when(patientManagementProxy.askExistenceOfPatient(2L)).thenReturn(true);
		Note note = new Note(2L, "Ceci est une note existante");
		String jsonResponse = mockMvc
				.perform(post("/rest/note").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(note)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		Long noteId = ((Integer) JsonPath.parse(jsonResponse).read("$.noteId")).longValue();

		Note updatedNote = new Note(2L, "Ceci est la note modifiée");

		// WHEN THEN
		mockMvc.perform(put("/rest/note/{id}", noteId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedNote))).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.practitionerNote").value("Ceci est la note modifiée"));
	}

	@WithMockUser
	@Test
	void givenANote_whenDeleteNote_thenReturns200() throws Exception {
		when(patientManagementProxy.askExistenceOfPatient(2L)).thenReturn(true);
		Note note = new Note(2L, "Ceci est une note existante");
		String jsonResponse = mockMvc
				.perform(post("/rest/note").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(note)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		Long noteId = ((Integer) JsonPath.parse(jsonResponse).read("$.noteId")).longValue();

		mockMvc.perform(delete("/rest/note/{id}", noteId)).andExpect(status().isOk());
	}

}
