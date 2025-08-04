package com.company.customerperson.service;

import com.company.customerperson.dto.CustomerDTO;
import com.company.customerperson.exception.ResourceNotFoundException;
import com.company.customerperson.model.Customer;
import com.company.customerperson.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setGender(customerDTO.getGender());
        customer.setAge(customerDTO.getAge());
        customer.setIdentification(customerDTO.getIdentification());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhone(customerDTO.getPhone());
        customer.setCustomerId(customerDTO.getIdentification());
        customer.setPassword(customerDTO.getPassword());
        customer.setStatus(customerDTO.getStatus());
        Customer saved = customerRepository.save(customer);
        return mapToDTO(saved);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return mapToDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        customer.setName(customerDTO.getName());
        customer.setGender(customerDTO.getGender());
        customer.setAge(customerDTO.getAge());
        customer.setIdentification(customerDTO.getIdentification());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhone(customerDTO.getPhone());
        customer.setCustomerId(customerDTO.getIdentification());
        customer.setPassword(customerDTO.getPassword());
        customer.setStatus(customerDTO.getStatus());
        Customer updated = customerRepository.save(customer);
        return mapToDTO(updated);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        if(customer!=null){
            customerRepository.deleteById(id);
        }
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setGender(customer.getGender());
        dto.setAge(customer.getAge());
        dto.setIdentification(customer.getIdentification());
        dto.setAddress(customer.getAddress());
        dto.setPhone(customer.getPhone());
        dto.setCustomerId(customer.getCustomerId());
        dto.setPassword(customer.getPassword());
        dto.setStatus(customer.getStatus());
        return dto;
    }
}