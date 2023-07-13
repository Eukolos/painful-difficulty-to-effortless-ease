package com.eukolos.restapiwithspringboot.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
private final CustomerService service;

    @GetMapping()
    public List<Customer> getCustomerList(){
        return service.getCustomerList();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Integer id){
        return service.getCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer saveCustomer(@RequestBody Customer customer){
        return service.saveCustomer(customer);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer){
        return service.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Integer id){
        service.deleteCustomer(id);
    }
}
