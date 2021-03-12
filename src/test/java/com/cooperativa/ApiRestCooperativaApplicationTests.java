package com.cooperativa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.cooperativa.domain.Pauta;
import com.cooperativa.domain.Votacao;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ApiRestCooperativaApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	/*
	 * Cadastrando uma nova pauta, status 201.
	 * */
	@Test
	void case1() throws Exception {
		Pauta pauta = new Pauta(null, "Sessão para votação do novo presidente do conselho.");
		mockMvc.perform(post("/pautas").contentType("application/json").content(objectMapper.writeValueAsString(pauta)))
				.andExpect(status().isCreated());
	}

	/*
	 * Cadastrando uma nova pauta, passando valor vazio, status 400
	 * */
	@Test
	void case2() throws Exception {
		Pauta pauta = new Pauta(null, "");
		mockMvc.perform(post("/pautas").contentType("application/json").content(objectMapper.writeValueAsString(pauta)))
				.andExpect(status().isBadRequest());

	}

	/*
	 * Cadastrando uma nova pauta, sema tag "pauta", status 400
	 * */
	@Test
	void case3() throws Exception {
		mockMvc.perform(post("/pautas").contentType("application/json").content(objectMapper.writeValueAsString(null)))
				.andExpect(status().isBadRequest());

	}

	/*
	 * Consultando uma pauta sem passar o Id, status 405
	 * */
	@Test
	void case4() throws Exception {
		mockMvc.perform(get("/pautas/")
				.contentType("application/json")).andExpect(status().isMethodNotAllowed());
	}
	
	/*
	 * Consultando uma pauta, status 200
	 * */
	@Test
	void case5() throws Exception {
		mockMvc.perform(get("/pautas/1")
				.contentType("application/json")).andExpect(status().isOk());
	}
	
	/*
	 * Consultando uma pauta, status 404
	 * */
	@Test
	void case6() throws Exception {
		mockMvc.perform(get("/pautas/20")
				.contentType("application/json")).andExpect(status().isNotFound());
	}
	
	/*
	 * Consultando resultado votação, status 404
	 * */
	@Test
	void case7() throws Exception {
		mockMvc.perform(get("/pautas/resultadoVotacao/20")
				.contentType("application/json")).andExpect(status().isNotFound());
	}
	
	/*
	 * Consultando resultado votação sem passar o Id, status 400
	 * */
	@Test
	void case8() throws Exception {
		mockMvc.perform(get("/pautas/resultadoVotacao/")
				.contentType("application/json")).andExpect(status().isBadRequest());
	}
	
	/*
	 * Votando em uma nova pauta, status 201.
	 * */
	@Test
	void case9() throws Exception {
		Votacao voto = new Votacao(null, "28666189045","Sim",1);
		mockMvc.perform(post("/pautas/votacao").contentType("application/json").content(objectMapper.writeValueAsString(voto)))
				.andExpect(status().isOk());
	}
	
	/*
	 * Votando em uma nova pauta. Mas passando "cpfAssociado"  vazio, status 400.
	 * */
	@Test
	void case10() throws Exception {
		Votacao voto = new Votacao(null, "","Sim",1);
		mockMvc.perform(post("/pautas/votaca").contentType("application/json").content(objectMapper.writeValueAsString(voto)))
				.andExpect(status().isBadRequest());
	}
	
	/*
	 * Votando em uma nova pauta. Mas passando a endpoint errado, status 404.
	 * */
	@Test
	void case11() throws Exception {
		Votacao voto = new Votacao(null, "28666189045","Sim",1);
		mockMvc.perform(post("/votacao").contentType("application/json").content(objectMapper.writeValueAsString(voto)))
				.andExpect(status().isNotFound());
	}
}
