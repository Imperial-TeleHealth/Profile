package doc.ic.profile;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@RequestMapping("api/profile")
public class CustomerController {

  @Value("${JWT_KEY}")
  private String secretKey;
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/greet")
  public GreetResponse greet() {
    return customerService.greet();
  }

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequest request) {
    customerService.signup(request);
    return getMapResponseEntity(request.email());

  }

  private ResponseEntity<Map<String, Object>> getMapResponseEntity(String email) {
    String jwtToken = generateJwtToken(email);
    Map<String, Object> response = new HashMap<>();
    response.put("ok", true);
    response.put("token", jwtToken);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
    if (!customerService.login(request)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return getMapResponseEntity(request.email());
  }

  private String generateJwtToken(String email) {
    Instant now = Instant.now();
    Instant expiry = now.plus(1, ChronoUnit.HOURS); // Token expiry in 1 hour

    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expiry))
        .signWith(getSecretKey())
        .compact();
  }

  private SecretKey getSecretKey() {
    // Use a secret key for signing JWT tokens
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  @GetMapping
  public List<Customer> getCustomer() {
    return customerService.getCustomer();
  }

  @PostMapping
  public void addCustomer(@RequestBody NewCustomerRequest request) {
    customerService.addCustomer(request);
  }

  @DeleteMapping("/del")
  public void deleteCustomer(@RequestBody DeleteCustomerRequest request) {
    customerService.deleteCustomer(request);
  }

  @PutMapping("/update")
  public void updateCustomer(@RequestBody UpdateCustomerRequest request) {
    customerService.updateCustomer(request);
  }
}
