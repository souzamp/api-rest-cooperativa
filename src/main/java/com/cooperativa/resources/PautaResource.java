package com.cooperativa.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pautas")
public class PautaResource {

	@RequestMapping(method = RequestMethod.GET)
	public String pautas() {
		return "Rest está funcionando!";
	}
}
