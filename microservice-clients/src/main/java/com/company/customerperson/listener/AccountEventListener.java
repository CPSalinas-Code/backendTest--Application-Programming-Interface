package com.company.customerperson.listener;

import com.company.customerperson.dto.events.AccountCreatedEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AccountEventListener.class);

    // Esta anotación hace que este método escuche la cola especificada.
    // Spring lo invoca automáticamente cuando llega un mensaje.
    @RabbitListener(queues = "accounts.created.queue")
    public void handleAccountCreatedEvent(AccountCreatedEventDTO event) {
        logger.info("Evento recibido al crear una cuenta en el microservicio-accounts: {}", event.toString());

        // Aquí podríamos añadir lógica de negocio si fuera necesario.
        // Por ejemplo, marcar al cliente como "activo" o enviarle un email de bienvenida.
        // Por ahora, solo registrar el evento es suficiente para demostrar la comunicación.
    }
}