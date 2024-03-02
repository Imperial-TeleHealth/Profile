package doc.ic.profile;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private String PRIVATE_KEY;

  @Value("${JWT_PUB_KEY}")
  private String PUBLIC_KEY;

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequest request) {
    customerService.signup(request);
    try {
      return getMapResponseEntity(request.email());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
    if (!customerService.login(request)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    try {
      return getMapResponseEntity(request.email());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping
  public List<Customer> getCustomer() {
    return customerService.getCustomer();
  }

  @DeleteMapping("/del")
  public void deleteCustomer(@RequestBody DeleteCustomerRequest request) {
    customerService.deleteCustomer(request);
  }

  @PutMapping("/update")
  public void updateCustomer(@RequestBody UpdateCustomerRequest request) {
    customerService.updateCustomer(request);
  }

  private String generateJwtToken(String email)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY));
    KeyFactory kf = KeyFactory.getInstance("RSA");

    // Create a private key object from string
    PrivateKey privateKey = kf.generatePrivate(spec);
    Instant now = Instant.now();
    Instant expiry = now.plus(1, ChronoUnit.HOURS); // Token expiry in 1 hour

    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expiry))
        .signWith(privateKey, SignatureAlgorithm.RS256)
        .compact();
  }

  private ResponseEntity<Map<String, Object>> getMapResponseEntity(String email)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    String jwtToken = generateJwtToken(email);
    Map<String, Object> response = new HashMap<>();
    response.put("ok", true);
    response.put("token", jwtToken);
    response.put("public_key", PUBLIC_KEY);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

}
