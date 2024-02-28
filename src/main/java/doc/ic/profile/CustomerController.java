package doc.ic.profile;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customers")
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

  @GetMapping
  public List<Customer> getCustomer() {
    return customerService.getCustomer();
  }

  @PostMapping
  public void addCustomer(@RequestBody NewCustomerRequest request) {
    customerService.addCustomer(request);
  }

  @DeleteMapping("{customerUsername}")
  public void deleteCustomer(@PathVariable("customerUsername") String username) {
    customerService.deleteCustomer(username);
  }

  @PutMapping("{customerUsername}")
  public void updateCustomer(
      @RequestBody UpdateCustomerRequest request,
      @PathVariable("customerUsername") String username) {
    customerService.updateCustomer(request, username);
  }
}
