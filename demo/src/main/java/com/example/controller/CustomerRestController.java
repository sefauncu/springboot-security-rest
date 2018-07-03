package com.example.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Customer;
import com.example.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;

@RestController
@RequestMapping("/api")
@Api(value = "CustomersControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	

	@ApiOperation(value ="all customers")
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Successful")
	})
	@GetMapping("/customerAll")
	public List<Customer> getCustomers() {

		return customerService.getCustomers();

	}	
	
	
	
	@ApiOperation(value ="gets the customer with specific ID")
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Successful"),
			@ApiResponse(code=400,message="ID not found")	
	})
	@GetMapping("/customerGet/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {

		Customer theCustomer = customerService.getCustomer(customerId);

		if (theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}

		return theCustomer;
	}


	
	@ApiOperation(value ="delete the customer with specific ID")
	@DeleteMapping("/customerDelete/{customerId}")
	@Secured({ "ROLE_ADMIN" })
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Successful"),
			@ApiResponse(code=400,message="ID not found"),
			@ApiResponse(code=404,message="user has no authority")
	})
	public void deleteCustomer(@PathVariable int customerId) {
		
		customerService.deleteCustomer(customerId);
	}


	@ApiOperation(value ="new customer registration")
	@PostMapping("/customerPost")
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Successful"),
			//@ApiResponse(code=400,message="ID not found"),
			@ApiResponse(code=404,message="user has no authority")
	})
	public Customer addCustomer(@RequestBody Customer theCustomer) {

		// aynı zamanda JSON'da bir kimliği geçmesi durumunda ... kimliği 0
		// olarak ayarla
		// bu, güncelleme yerine yeni öğenin kaydedilmesini zorlar.

		theCustomer.setId(0);

		customerService.saveCustomer(theCustomer);

		return theCustomer;
	}
	
	
	@ApiOperation(value ="update customer")
	@PutMapping("/customerPut")
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Successful"),
			//@ApiResponse(code=400,message="ID not found"),
			@ApiResponse(code=404,message="user has no authority")
	})
	public Customer updateCustomer(@RequestBody Customer theCustomer) {

		customerService.saveCustomer(theCustomer);

		return theCustomer;

	}

}
