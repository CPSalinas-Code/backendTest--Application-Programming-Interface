package com.company.customerperson.service;

import com.company.customerperson.dto.PersonDTO;
import com.company.customerperson.model.Person;
import com.company.customerperson.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setGender(personDTO.getGender());
        person.setAge(personDTO.getAge());
        person.setIdentification(personDTO.getIdentification());
        person.setAddress(personDTO.getAddress());
        person.setPhone(personDTO.getPhone());
        Person saved = personRepository.save(person);
        return mapToDTO(saved);
    }

    @Override
    public PersonDTO getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return mapToDTO(person);
    }

    @Override
    public List<PersonDTO> getAllPerson() {
        return personRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setName(personDTO.getName());
        person.setGender(personDTO.getGender());
        person.setAge(personDTO.getAge());
        person.setIdentification(personDTO.getIdentification());
        person.setAddress(personDTO.getAddress());
        person.setPhone(personDTO.getPhone());
        Person updated = personRepository.save(person);
        return mapToDTO(updated);
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    private PersonDTO mapToDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setGender(person.getGender());
        dto.setAge(person.getAge());
        dto.setIdentification(person.getIdentification());
        dto.setAddress(person.getAddress());
        dto.setPhone(person.getPhone());
        return dto;
    }
}