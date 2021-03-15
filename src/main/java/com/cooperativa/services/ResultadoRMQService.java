package com.cooperativa.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.cooperativa.resources.PautaResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ConfigurationProperties
public class ResultadoRMQService {
	private static Logger logger = LoggerFactory.getLogger(PautaResource.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendResultadoToRabbit(Object object) {
		logger.debug("sendResultadoToRabbit - begin");
		try {			
			String json = new ObjectMapper().writeValueAsString(object);
			String exchange = rabbitTemplate.getExchange();
			String routingKey = rabbitTemplate.getRoutingKey();

			rabbitTemplate.convertAndSend(exchange, routingKey, json);
		} catch (JsonProcessingException e) {
			logger.error("Erro ao enviar para RabbitMQ.", e);

			e.printStackTrace();
		}
	}
}
