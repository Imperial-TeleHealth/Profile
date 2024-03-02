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

  public void deleteCustomer(DeleteCustomerRequest request) {
    customerRepository.deleteById(request.email());
  }

  public void updateCustomer(UpdateCustomerRequest request) {
    String email = request.email();
    Optional<Customer> customer = customerRepository.findById(email);
    if (customer.isPresent()) {
      customer.get().setName(request.name());
      customer.get().setDateOfBirth(request.dateOfBirth());
      customerRepository.save(customer.get());
    }
  }

  public void signup(SignupRequest request) {
    Customerpassword customerPassword = new Customerpassword();
    customerPassword.setEmail(request.email());
    customerPassword.setPassword(request.password());
    customerPasswordRepository.save(customerPassword);
  }

  public boolean login(LoginRequest request) {
    Optional<Customerpassword> customerPassword =
        customerPasswordRepository.findById(request.email());
    return customerPassword
        .map(customerpassword -> customerpassword.getPassword().equals(request.password()))
        .orElse(false);
  }
}
