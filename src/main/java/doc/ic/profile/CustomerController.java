package doc.ic.profile;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/profile")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/greet")
  public GreetResponse greet() {
    return customerService.greet();
  }

  @PostMapping("/signup")
  public void signup(@RequestBody SignupRequest request) {
    customerService.signup(request);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
    Map<String, Object> response = Map.of("ok", customerService.login(request));
    return ResponseEntity.status(HttpStatus.OK).body(response);
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
  public void updateCustomer(
      @RequestBody UpdateCustomerRequest request) {
    customerService.updateCustomer(request);
  }
}
