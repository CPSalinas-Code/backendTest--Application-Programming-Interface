package com.company.customerperson.controller;

import com.company.customerperson.dto.PersonDTO;
import com.company.customerperson.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public PersonDTO createPerson(@Valid @RequestBody PersonDTO personDTO) {
        PersonDTO created = personService.createPerson(personDTO);
        return ResponseEntity.ok(created).getBody();
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping
    public List<PersonDTO> getAllPerson() {
        return personService.getAllPerson();
    }

    @PutMapping("/{id}")
    public PersonDTO updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        return personService.updatePerson(id, personDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }
}