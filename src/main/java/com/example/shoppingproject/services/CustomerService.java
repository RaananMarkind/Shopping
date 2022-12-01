package com.example.shoppingproject.services;

import com.example.shoppingproject.repository.UserRepository;
import com.example.shoppingproject.beans.Customer;
import com.example.shoppingproject.beans.Product;
import com.example.shoppingproject.beans.Users;
import com.example.shoppingproject.enums.ClientType;
import com.example.shoppingproject.exceptions.ErrMsg;
import com.example.shoppingproject.exceptions.SystemException;
import com.example.shoppingproject.repository.CustomerRepository;
import com.example.shoppingproject.repository.ProductRepository;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerService implements CustomerServiceInterface {
	
	 /**
     * Methods
     *
     * login
     *
     * get details update delete
     *
     * get one all by prices dates rating type, name
     * add to cart remove from  cart
     * purchase cart
     *
     */


    private int id = -1;

    private CustomerRepository customerRepository;

    private ProductRepository productRepository;

    private UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public int getId() {
        return id;
    }

	@Override
	public Customer login(String userName, String password) throws SystemException {
		// TODO Login method by user name and password
		if(userRepository.findClientTypeByUserNameAndPassword(userName, password) == ClientType.CUSTOMER) {
			Customer customer = customerRepository.findByUserNameAndPassword(userName, password);
			this.id = customer.getId();
			return customer;
		}
		 else throw new SystemException(ErrMsg.ID_NOT_FOUND);
	}

	@Override
	public Customer getDetails() throws SystemException{
		// TODO get details of the customer
		return customerRepository.findById(id).orElseThrow(()->new SystemException(ErrMsg.ID_NOT_FOUND));
	}

	@Override
	public Customer updateCustomer(Customer customer) throws SystemException {
		// TODO updating a customers personal details
		
		String email = customer.getEmail();
		String password = customer.getPassword();
		String firstName = customer.getFirstName();
		String lastName = customer.getLastName();
		Date date = new Date(System.currentTimeMillis());
		List<Users> users = userRepository.findAll();
		
		//TODO check if user name already exists
		for (Users users2 : users) {
			if(users2.getUserName().equals(customer.getUserName())) {
				throw new SystemException(ErrMsg.CUSTOMER_UPDATE_FAILURE);
			}
		}
		
		//TODO check if email, password, first name, last name and birthday are valid 
		if((email.length() < 8 || email.length() > 30) || 
				(password.length() < 4 || password.length() > 16) || 
					(firstName.length() < 3 || firstName.length() > 10)|| 
						(lastName.length() < 3 || lastName.length() > 10) ||
							customer.getBirthDate().before(date) ) {
			throw new SystemException(ErrMsg.CUSTOMER_UPDATE_FAILURE);
		}
		
		return customerRepository.save(customer);
	}

	@Override
	public boolean deleteCustomer() {
		// TODO Customer deletes himself from the application
		
		//delete from users table
		//remove from customer table
		//remove from customer_cart table 
		//remove from customers_products table
		
		productRepository.deleteCustomerProductsHistory(this.id);
		productRepository.deleteCustomerCartHistory(this.id);
		customerRepository.deleteById(this.id);
		userRepository.deleteById(this.id);
		return true;
	}

	@Override
	public Product getOneProduct(int id) throws SystemException {
		// TODO Get one product by a customer
		
		//check if id is valid
		//if valid check if product id matches customer id
		
		Product product = productRepository.findById(id).orElseThrow(()-> new SystemException(ErrMsg.ID_NOT_FOUND));
		if(product != null) {
			product = productRepository.getProdcutByCustomerIdAndProductId(this.id, id);
		}
		
		if(product != null) {
			return product;
		}
		else {
			throw new SystemException(ErrMsg.PRODUCT_EXIST);
		}

	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsBetweenPrices(double minPrice, double maxPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAProductsBetweenDates(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getTopRatingProducts(int numOfProducts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByName(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> addProductToCart(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> removeProductFromCart(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean clearCart() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean purchaseCart() {
		// TODO Auto-generated method stub
		return false;
	}

}
