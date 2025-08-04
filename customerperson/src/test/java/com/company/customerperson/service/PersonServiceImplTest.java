package com.company.customerperson.service;

import com.company.customerperson.dto.PersonDTO;
import com.company.customerperson.model.Person;
import com.company.customerperson.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePerson() {
        System.out.println("Iniciando test: testCreatePerson");
        // Arrange
        PersonDTO dto = new PersonDTO();
        dto.setName("Juan Perez");
        dto.setGender("M");
        dto.setAge(30);
        dto.setIdentification("1234567890");
        dto.setAddress("Calle Falsa 123");
        dto.setPhone("0999999999");

        Person person = new Person();
        person.setId(1L);
        person.setName(dto.getName());
        person.setGender(dto.getGender());
        person.setAge(dto.getAge());
        person.setIdentification(dto.getIdentification());
        person.setAddress(dto.getAddress());
        person.setPhone(dto.getPhone());

        when(personRepository.save(any(Person.class))).thenReturn(person);

        // Act
        PersonDTO result = personService.createPerson(dto);

        // Assert
        System.out.println("Resultado: " + result);
        assertNotNull(result);
        assertEquals("Juan Perez", result.getName());
        assertEquals("1234567890", result.getIdentification());
        System.out.println("testCreatePerson finalizado correctamente\n");
    }

    @Test
    void testGetPersonById() {
        System.out.println("Iniciando test: testGetPersonById");
        // Arrange
        Person person = new Person();
        person.setId(1L);
        person.setName("Juan Perez");
        person.setGender("M");
        person.setAge(30);
        person.setIdentification("1234567890");
        person.setAddress("Calle Falsa 123");
        person.setPhone("0999999999");

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        // Act
        PersonDTO result = personService.getPersonById(1L);

        // Assert
        System.out.println("Resultado: " + result);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan Perez", result.getName());
        System.out.println("testGetPersonById finalizado correctamente\n");
    }

    @Test
    void testUpdatePerson_FailsWhenNameIsNotUpdated() {
        System.out.println("Iniciando test: testUpdatePerson_FailsWhenNameIsNotUpdated");
        // Arrange
        Long id = 1L;
        PersonDTO dto = new PersonDTO();
        dto.setName("Juan Perez Modificado");
        dto.setGender("M");
        dto.setAge(31);
        dto.setIdentification("1234567890");
        dto.setAddress("Calle Nueva 456");
        dto.setPhone("0999999998");

        Person existing = new Person();
        existing.setId(id);
        existing.setName("Juan Perez");
        existing.setGender("M");
        existing.setAge(30);
        existing.setIdentification("1234567890");
        existing.setAddress("Calle Falsa 123");
        existing.setPhone("0999999999");

        // El mock devuelve el objeto sin actualizar el nombre
        when(personRepository.findById(id)).thenReturn(Optional.of(existing));
        when(personRepository.save(any(Person.class))).thenReturn(existing);

        // Act
        PersonDTO result = personService.updatePerson(id, dto);

        // Assert
        System.out.println("Resultado esperado: Juan Perez Modificado");
        System.out.println("Resultado real: " + result.getName());
        assertEquals("Juan Perez Modificado", result.getName());
        System.out.println("testUpdatePerson_FailsWhenNameIsNotUpdated finalizado\n");
    }
}