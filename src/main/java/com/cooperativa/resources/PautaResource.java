package com.cooperativa.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativa.domain.Pauta;

@RestController
@RequestMapping(value = "/pautas")
public class PautaResource {

	@RequestMapping(method = RequestMethod.GET)
	public List<Pauta> pautas() {

		Pauta pauta1 = new Pauta(1, "Seção para  aprovar orçamento 2021.");

		List<Pauta> lista = new ArrayList<>();
		lista.add(pauta1);

		return lista;
	}
}
