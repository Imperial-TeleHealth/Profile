package doc.ic.profile;

import java.util.List;
import java.util.Optional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }


  public List<Customer> getCustomer() {
    return customerRepository.findAll();
  }

  public void addCustomer(NewCustomerRequest request) {
    Customer customer = new Customer();
    customer.setUsername(request.username());
    customer.setName(request.name());
    customer.setEmail(request.email());
    customer.setAge(request.age());
    customerRepository.save(customer);
  }

  public void deleteCustomer(String username) {
    customerRepository.deleteById(username);
  }

  public void updateCustomer(UpdateCustomerRequest request, String username) {
    Optional<Customer> customer = customerRepository.findById(username);
    if (customer.isPresent()) {
      customer.get().setName(request.name());
      customer.get().setEmail(request.email());
      customer.get().setAge(request.age());
      customerRepository.save(customer.get());
    }
  }

  public GreetResponse greet() {
    return new GreetResponse("Hello", List.of("java", " c++"), new Person("Daniel"));
  }
}
