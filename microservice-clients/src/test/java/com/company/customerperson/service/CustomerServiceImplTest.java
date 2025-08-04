package com.company.customerperson.service;

import com.company.customerperson.dto.CustomerDTO;
import com.company.customerperson.model.Customer;
import com.company.customerperson.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Usamos Mockito para realizar pruebas
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImplTest.class);

    // @Mock crea una simulación del repositorio
    @Mock(lenient = true)
    private CustomerRepository customerRepository;

    @Mock(lenient = true)
    private ModelMapper modelMapper;

    // @InjectMocks crea una instancia real del servicio con los mocks inyectados.
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void whenCreateCustomer_shouldReturnCustomerDTO() {
        // Preparar el escenario de la prueba
        log.info("--- ARRANGE ---");
        log.info("1. Creando el DTO de entrada para la prueba.");
        CustomerDTO customerToCreate = new CustomerDTO();
        customerToCreate.setName("Christian Paul");
        customerToCreate.setCustomerId("test-123");

        log.info("2. Creando las entidades que simularán la respuesta del repositorio.");
        Customer customerEntity = new Customer();
        customerEntity.setName("Christian Paul");
        customerEntity.setCustomerId("test-123");

        Customer savedCustomerEntity = new Customer();
        savedCustomerEntity.setId(1L);
        savedCustomerEntity.setName("Christian Paul");
        savedCustomerEntity.setCustomerId("test-123");

        CustomerDTO expectedDto = new CustomerDTO();
        expectedDto.setId(1L);
        expectedDto.setName("Christian Paul");
        expectedDto.setCustomerId("test-123");

        log.info("3. Configurando el comportamiento de los mocks (when...thenReturn).");
        when(modelMapper.map(any(CustomerDTO.class), any())).thenReturn(customerEntity);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomerEntity);
        when(modelMapper.map(any(Customer.class), any())).thenReturn(expectedDto);

        log.info("Ejecutando el método customerService.createCustomer().");
        CustomerDTO result = customerService.createCustomer(customerToCreate);
        log.info("Método ejecutado. Resultado obtenido: {}", result);

        //Verificar los resultados
        log.info("--- ASSERT ---");
        log.info("Verificando que el resultado no sea nulo.");
        assertNotNull(result);
        log.info("Verificando que el ID sea el esperado (1L).");
        assertEquals(1L, result.getId());
        log.info("Verificando que el nombre sea el esperado ('Christian Paul').");
        assertEquals("Christian Paul", result.getName());
        log.info("Prueba finalizada con éxito.");
    }
}