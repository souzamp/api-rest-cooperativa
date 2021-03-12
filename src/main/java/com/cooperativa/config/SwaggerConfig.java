package com.cooperativa.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private final ResponseMessage m200= simpleMessage(200, "OK");
	private final ResponseMessage m201= customMessage1();
	private final ResponseMessage m400= simpleMessage(400, "Bad Request");
	private final ResponseMessage m404= simpleMessage(404, "Not Found");
	private final ResponseMessage m405= simpleMessage(405, "Method Not Allowed");
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m400, m200, m404))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m400, m405))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cooperativa.resources"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() { 
		return new ApiInfo(
				"API Cooperativa",
				"Essa api é utilizada para criar uma pauta para uma cooperativa, e abrir votação nessa pauta",
				"Versão 1.0",
				"https://github.com/souzamp/api-rest-cooperativa",
				new Contact("Marcos Paulo", "https://github.com/souzamp", "marcos.souza.anl@gmail.com"),
				"Uso permitido.",
				"www.linkedin.com/in/marcos-paulo-aa4287b7",
				Collections.emptyList()
				);
	}
	
	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
		}
	
	private ResponseMessage customMessage1() {
		Map<String, Header> map= new HashMap<>();
		map.put("location", new Header("location", "URI do novo recurso", new ModelRef("string")));
		return new ResponseMessageBuilder()
				.code(201)
				.message("Recurso criado")
				.headersWithDescription(map)
				.build();
		}
}
