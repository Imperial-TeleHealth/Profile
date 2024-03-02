package doc.ic.profile;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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

  public Customer getCustomer(@NotNull GetCustomerRequest request) throws SignatureException {
    String jwt = request.jwt();
    String email = jwtUtil.extractEmail(jwt);
    return customerRepository.findById(email).orElse(null);
  }

  public void deleteCustomer(@NotNull DeleteCustomerRequest request) {
    customerRepository.deleteById(request.email());
  }

  public void updateCustomer(@NotNull UpdateCustomerRequest request) {
    String email = request.email();
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
