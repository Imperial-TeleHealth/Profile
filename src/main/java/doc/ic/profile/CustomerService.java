package doc.ic.profile;

import java.security.SignatureException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    if (customerPasswordRepository.findById(email).orElse(null) != null) {
      return customerRepository.findById(email).orElse(null);
    }
    return null;
  }

  public void deleteCustomer(String jwt) throws SignatureException {
    String email = jwtUtil.extractEmail(jwt);
    customerRepository.deleteById(email);
    customerPasswordRepository.deleteById(email);
  }

  public void updateCustomer(String jwtToken, @NotNull UpdateCustomerRequest request)
      throws SignatureException {
    String email = jwtUtil.extractEmail(jwtToken);
    Optional<Customer> customer = customerRepository.findById(email);
    if (customer.isPresent()) {
      customer.get().setName(request.name());
      customer.get().setDateOfBirth(request.dateOfBirth());
      customerRepository.save(customer.get());
    }
  }

  public void signup(@NotNull SignupRequest request) {
    Customerpassword customerPassword = new Customerpassword();
    Customer customer = new Customer();
    customer.setEmail(request.email());
    customer.setName(request.name());
    customer.setDateOfBirth(request.dateOfBirth());
    customerPassword.setEmail(request.email());
    customerPassword.setHashedPassword(request.hashedPassword());
    customerRepository.save(customer);
    customerPasswordRepository.save(customerPassword);
  }

  public boolean login(@NotNull LoginRequest request) {
    Optional<Customerpassword> customerPassword =
        customerPasswordRepository.findById(request.email());
    return customerPassword
        .map(
            customerpassword ->
                customerpassword.getHashedPassword().equals(request.hashedPassword()))
        .orElse(false);
  }

  public List<String> getAllDoctor() {
    return customerRepository.findAll().stream()
        .filter(customer -> Boolean.TRUE.equals(customer.getIsDoctor()))
        .map(Customer::getEmail)
        .collect(Collectors.toList());
  }
}
