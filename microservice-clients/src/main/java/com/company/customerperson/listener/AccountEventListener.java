package com.company.customerperson.listener;

import com.company.customerperson.dto.events.AccountCreatedEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AccountEventListener.class);

    // la anotacion hace que el metodo escuche la cola de mensajes
    // Spring lo invoca automáticamente cuando llega un mensaje.
    @RabbitListener(queues = "accounts.created.queue")
    public void handleAccountCreatedEvent(AccountCreatedEventDTO event) {


        logger.info("Comunicacion Asincrona RabbitMQ");
        logger.info("Evento recibido al crear una cuenta en el microservicio-accounts: {}", event.toString());

        // Se puede anadir mas logica de negocio, por el momento es una muestra de lo que pide el requerimiento sobre comunicacion asincrona
    }
}