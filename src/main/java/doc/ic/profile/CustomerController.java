package doc.ic.profile;

import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/profile")
public class CustomerController {

  private final CustomerService customerService;
  private final JwtUtil jwtUtil;

  public CustomerController(CustomerService customerService, JwtUtil jwtUtil) {
    this.customerService = customerService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequest request) {
    customerService.signup(request);
    try {
      return jwtUtil.getMapResponseEntity(request.email());
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
      return jwtUtil.getMapResponseEntity(request.email());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/get")
  public ResponseEntity<Map<String, Object>> getCustomer(@RequestBody GetCustomerRequest request)
      throws SignatureException {
    HashMap<String, Object> map = new HashMap<>();
    try {
      Customer response = customerService.getCustomer(request);
      map.put("email", response.getEmail());
      map.put("name", response.getName());
      map.put("dateOfBirth", response.getDateOfBirth());
      // TODO: Do I generate a jwt to renew session?
      return ResponseEntity.ok(map);
    } catch (SignatureException e) {
      map.put("error", "JWT signature verification failed: " + e.getMessage() + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }
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
