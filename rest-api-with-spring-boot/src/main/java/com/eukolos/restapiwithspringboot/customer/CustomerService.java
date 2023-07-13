package com.eukolos.restapiwithspringboot.customer;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public Customer saveCustomer(Customer customer){
        return repository.save(customer);
    }

    public List<Customer> getCustomerList(){
        return repository.findAll();
    }

    public Customer getCustomerById(Integer id){
        return repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Customer not founded with this id: {}" + id));
    }

    public Customer updateCustomer(Customer customer){
        Customer fromDB = repository.findById(customer.getId()).orElseThrow(()-> new EntityNotFoundException("Customer not founded with this id: {}" + customer.getId()));
        return repository.save(
                Customer.builder()
                        .id(fromDB.getId())
                        .name(customer.getName())
                        .build()
        );
    }

    public void deleteCustomer(Integer id){
        repository.deleteById(id);
    }
}
