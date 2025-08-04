package com.company.customerperson.service;

import com.company.customerperson.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    PersonDTO createPerson(PersonDTO personDTO);
    PersonDTO getPersonById(Long id);
    List<PersonDTO> getAllPerson();
    PersonDTO updatePerson(Long id, PersonDTO personDTO);
    void deletePerson(Long id);
}