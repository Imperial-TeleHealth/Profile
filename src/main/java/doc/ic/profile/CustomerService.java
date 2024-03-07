package doc.ic.profile;

import java.security.SignatureException;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerPasswordRepository customerPasswordRepository;
  private final JwtUtil jwtUtil;

  public CustomerService(
      CustomerRepository customerRepository,
      CustomerPasswordRepository customerPasswordRepository,
      JwtUtil jwtUtil) {
    this.customerRepository = customerRepository;
    this.customerPasswordRepository = customerPasswordRepository;
    this.jwtUtil = jwtUtil;
  }

  public Customer getCustomer(String jwtToken) throws SignatureException {
    String email = jwtUtil.extractEmail(jwtToken);
    return customerRepository.findById(email).orElse(null);
  }

  public void deleteCustomer(@NotNull DeleteCustomerRequest request) throws SignatureException {
    String email = jwtUtil.extractEmail(request.jwt());
    customerRepository.deleteById(email);
  }

  public void updateCustomer(@NotNull UpdateCustomerRequest request) throws SignatureException {
    String email = jwtUtil.extractEmail(request.jwt());
    Optional<Customer> customer = customerRepository.findById(email);
    if (customer.isPresent()) {
      customer.get().setName(request.name());
      customer.get().setDateOfBirth(request.dateOfBirth());
      customerRepository.save(customer.get());
    }
  }

  public void signup(@NotNull SignupRequest request) {
    Customerpassword customerPassword = new Customerpassword();
    customerPassword.setEmail(request.email());
    customerPassword.setPassword(request.password());
    customerPasswordRepository.save(customerPassword);
  }

  public boolean login(@NotNull LoginRequest request) {
    Optional<Customerpassword> customerPassword =
        customerPasswordRepository.findById(request.email());
    return customerPassword
        .map(customerpassword -> customerpassword.getPassword().equals(request.password()))
        .orElse(false);
  }
}
