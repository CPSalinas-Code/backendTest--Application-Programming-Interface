package com.company.accountsmovements.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase que configura RabbitMQ para comunicacion sincrona,
 * cumple la funcion de enviar un mensaje al servicio de clientes cuando se crea una cuenta en esta api
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@Configuration
public class RabbitMQConfig {

    //Nombre del exchange donde se publicarán los eventos.
    public static final String EXCHANGE_NAME = "events.exchange";
    //Nombre de la cola que almacenará los mensajes de cuentas creadas.
    public static final String QUEUE_NAME = "accounts.created.queue";
    //Clave de enrutamiento para los eventos de cuentas creadas.
    public static final String ROUTING_KEY = "account.created";


    // El exchange principal donde se publican todos los eventos
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }


    // La cola específica para los eventos de "cuenta creada"
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true); // true = durable queue
    }

    // Es el enlace que conecta el exchange con la cola, usando una clave
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}