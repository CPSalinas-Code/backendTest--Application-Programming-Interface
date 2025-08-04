package com.company.accountsmovements.restClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * RestClient usado para la validacion de la existencia del cliente en el microservice-clients
 * Se diferencia de la comunicacion asincrona requerida, ya que es necesario una validacion previa poara crear cuentas
 * ya no que es optimo tener cuentas creadas "en el aire" al no estas asociadas con cliente existes
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */


@FeignClient(name = "microservice-clients", url = "${client-service.base-url}")
public interface ClientServiceFeignClient {

    // Usamos las mismas anotaciones que en el Controller
    @GetMapping("/clientes/{id}")
    ResponseEntity<Void> customerExists(@PathVariable("id") Long id);
}