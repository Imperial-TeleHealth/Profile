import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import doc.ic.profile.Customer;
import doc.ic.profile.CustomerPasswordRepository;
import doc.ic.profile.CustomerRepository;
import doc.ic.profile.CustomerService;
import doc.ic.profile.Customerpassword;
import doc.ic.profile.LoginRequest;
import doc.ic.profile.NewCustomerRequest;
import doc.ic.profile.SignupRequest;
import doc.ic.profile.UpdateCustomerRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class CustomerServiceTest {

  @org.mockito.Mock public MockitoRule rule = MockitoJUnit.rule();

  private final CustomerRepository customerRepository = mock(CustomerRepository.class);
  private final CustomerPasswordRepository customerPasswordRepository =
      mock(CustomerPasswordRepository.class);

  private final List<Customer> customersList = new ArrayList<>();
  private final CustomerService customerService =
      new CustomerService(customerRepository, customerPasswordRepository);

  private final Customer customer = new Customer("username", "name", "email", 20);
  private final Customerpassword customerPassword = new Customerpassword("username", "password");

  @Test
  public void getCustomerReturnsListOfCustomers() {
    // Arrange
    customersList.add(customer);
    when(customerRepository.findAll()).thenReturn(customersList);
    // Act
    List<Customer> customers = customerService.getCustomer();
    // Assert
    Assertions.assertEquals(1, customers.size());
  }

  @Test
  public void addCustomerSavesCustomers() {
    // Arrange
    NewCustomerRequest request = new NewCustomerRequest("username", "name", "email", 20);
    // Act
    customerService.addCustomer(request);
    // Assert
    Customer save = Mockito.verify(customerRepository, times(1)).save(customer);
  }

  @Test
  public void deleteCustomerDeletesCustomers() {
    // Arrange
    String username = "username";
    // Act
    customerService.deleteCustomer(username);
    // Assert
    Mockito.verify(customerRepository, times(1)).deleteById(username);
  }

  @Test
  public void updateCustomerUpdatesCustomers() {
    // Arrange
    String username = customer.getUsername();
    UpdateCustomerRequest request = new UpdateCustomerRequest("name", "email2", 21);
    when(customerRepository.findById(username)).thenReturn(java.util.Optional.of(customer));
    // Act
    customerService.updateCustomer(request, "username");
    // Assert
    Assertions.assertEquals("name", customer.getName());
    Assertions.assertEquals("email2", customer.getEmail());
    Assertions.assertEquals(21, customer.getAge());
  }

  @Test
  public void signupSavesCustomerPassword() {
    // Arrange
    // Act
    customerService.signup(new SignupRequest("username", "password"));
    // Assert
    Mockito.verify(customerPasswordRepository, times(1)).save(customerPassword);
  }

  @Test
  public void loginReturnsTrueIfCustomerPasswordMatches() {
    // Arrange
    when(customerPasswordRepository.findById("username"))
        .thenReturn(java.util.Optional.of(customerPassword));
    // Act
    boolean login = customerService.login(new LoginRequest("username", "password"));
    // Assert
    Assertions.assertTrue(login);
  }

  @Test
  public void loginReturnsTrueIfCustomerPasswordDoesNotMatches() {
    // Arrange
    when(customerPasswordRepository.findById("username"))
        .thenReturn(java.util.Optional.of(customerPassword));
    // Act
    boolean login = customerService.login(new LoginRequest("username", "password1"));
    // Assert
    Assertions.assertFalse(login);
  }
}
