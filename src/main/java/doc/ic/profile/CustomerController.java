package doc.ic.profile;

import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

  @GetMapping("/greeting")
  public String greeting() {
    return "Hello, World!";
  }

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequest request) {
    customerService.signup(request);
    try {
      return jwtUtil.jwtResponse(request.email());
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
      return jwtUtil.jwtResponse(request.email());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/get")
  public ResponseEntity<Map<String, Object>> getCustomer(
      @RequestHeader("Authorization") String jwtToken) {
    HashMap<String, Object> map = new HashMap<>();
    try {
      Customer response = customerService.getCustomer(jwtToken);

      if (response == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      map.put("email", response.getEmail());
      map.put("name", response.getName());
      map.put("dateOfBirth", response.getDateOfBirth());
      return ResponseEntity.ok(map);
    } catch (SignatureException e) {
      map.put("error", "JWT signature verification failed: " + e.getMessage() + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }
  }

  @DeleteMapping("/del")
  public ResponseEntity<Map<String, Object>> deleteCustomer(
      @RequestHeader("Authorization") String jwtToken) {
    HashMap<String, Object> map = new HashMap<>();
    try {
      customerService.deleteCustomer(jwtToken);
      map.put("ok", true);
      map.put("message", "Customer deleted successfully");
      return ResponseEntity.ok(map);
    } catch (SignatureException e) {
      map.put("error", "JWT signature verification failed: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }
  }

  @PutMapping("/update")
  public ResponseEntity<HashMap<String, Object>> updateCustomer(
      @RequestHeader("Authorization") String jwtToken,
      @RequestBody UpdateCustomerRequest request) {
    HashMap<String, Object> map = new HashMap<>();
    try {
      customerService.updateCustomer(jwtToken, request);
      map.put("ok", true);
      map.put("message", "Customer updated successfully");
      return ResponseEntity.ok(map);
    } catch (SignatureException e) {
      map.put("error", "JWT signature verification failed: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }
  }

  @GetMapping("/getDoctor")
  public ResponseEntity<HashMap<String, Object>> getAllDoctor() {
    HashMap<String, Object> map = new HashMap<>();
    try {
      List<String> doctorList = customerService.getAllDoctor();
      map.put("doctors", doctorList);
      return ResponseEntity.ok(map);

    } catch (Exception e) {
      map.put("error", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }
  }
}
