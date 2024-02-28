package doc.ic.profile;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerPasswordRepository customerPasswordRepository;

  public CustomerService(
      CustomerRepository customerRepository,
      CustomerPasswordRepository customerPasswordRepository) {
    this.customerRepository = customerRepository;
    this.customerPasswordRepository = customerPasswordRepository;
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

  public void signup(SignupRequest request) {
    Customerpassword customerPassword = new Customerpassword();
    customerPassword.setUsername(request.username());
    customerPassword.setPassword(request.password());
    customerPasswordRepository.save(customerPassword);
  }
}
