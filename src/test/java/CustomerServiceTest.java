import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import doc.ic.profile.Customer;
import doc.ic.profile.CustomerPasswordRepository;
import doc.ic.profile.CustomerRepository;
import doc.ic.profile.CustomerService;
import doc.ic.profile.Customerpassword;
import doc.ic.profile.JwtUtil;
import doc.ic.profile.LoginRequest;
import doc.ic.profile.SignupRequest;
import doc.ic.profile.UpdateCustomerRequest;
import java.security.SignatureException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

public class CustomerServiceTest {

  //  @org.mockito.Mock public MockitoRule rule = MockitoJUnit.rule();
  @Mock private final Environment environment = mock(Environment.class);

  @Mock private final CustomerRepository customerRepository = mock(CustomerRepository.class);

  @Mock
  private final CustomerPasswordRepository customerPasswordRepository =
      mock(CustomerPasswordRepository.class);

  @Mock private final JwtUtil jwtUtil = mock(JwtUtil.class);

  private final CustomerService customerService =
      new CustomerService(customerRepository, customerPasswordRepository, jwtUtil);
  private final Customer customer = new Customer("email", "name", "01/03/2024");
  private final Customerpassword customerPassword = new Customerpassword("email", "password");

  @BeforeEach
  void setup() {
    when(environment.getProperty("JWT_KEY")).thenReturn("dummy_private_key");
    when(environment.getProperty("JWT_PUB_KEY")).thenReturn("dummy_public_key");
  }

  @Test
  public void getCustomerReturnsaCustomerWithTheCorrespondingEmail() throws SignatureException {
    // Arrange
    String jwt = "jwt";
    when(jwtUtil.extractEmail(jwt)).thenReturn("email");
    when(customerPasswordRepository.findById("email")).thenReturn(Optional.of(customerPassword));
    when(customerRepository.findById("email")).thenReturn(Optional.of(customer));

    // Act
    Customer result = customerService.getCustomer(jwt);

    // Assert
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result, customer);
  }

  @Test
  public void getCustomerReturnsNullIfJwtThrowException() throws SignatureException {
    // Arrange
    String jwt = "jwt";
    when(jwtUtil.extractEmail(jwt)).thenThrow(SignatureException.class);

    // Assert
    Assertions.assertThrows(
        SignatureException.class,
        () -> {
          customerService.getCustomer(jwt);
        });
  }

  @Test
  public void deleteCustomerDeletesCustomers() throws SignatureException {
    // Arrange
    String jwtToken = "jwt";
    when(jwtUtil.extractEmail(jwtToken)).thenReturn("email");

    // Act
    customerService.deleteCustomer(jwtToken);
    // Assert
    Mockito.verify(customerRepository, times(1)).deleteById("email");
  }

  @Test
  public void updateCustomerUpdatesCustomers() throws SignatureException {
    // Arrange
    String jwtToken = "jwt";
    Customer updatedCustomer = new Customer("email", "name", "02/03/2024");
    UpdateCustomerRequest request = new UpdateCustomerRequest("name", "02/03/2024");
    when(jwtUtil.extractEmail(jwtToken)).thenReturn("email");
    when(customerRepository.findById("email")).thenReturn(Optional.of(updatedCustomer));
    // Act
    customerService.updateCustomer(jwtToken, request);
    // Assert
    Assertions.assertEquals("name", updatedCustomer.getName());
    Assertions.assertEquals("02/03/2024", updatedCustomer.getDateOfBirth());
    Mockito.verify(customerRepository, times(1)).save(updatedCustomer);
  }

  @Test
  public void signupSavesCustomerPassword() {
    // Act
    customerService.signup(new SignupRequest("email", "password", "name", "01/03/2024"));
    // Assert
    Mockito.verify(customerPasswordRepository, times(1)).save(customerPassword);
  }

  @Test
  public void loginReturnsTrueIfCustomerPasswordMatches() {
    // Arrange
    when(customerPasswordRepository.findById("email")).thenReturn(Optional.of(customerPassword));
    // Act
    boolean login = customerService.login(new LoginRequest("email", "password"));
    // Assert
    Assertions.assertTrue(login);
  }

  @Test
  public void loginReturnsTrueIfCustomerPasswordDoesNotMatches() {
    // Arrange
    when(customerPasswordRepository.findById("username")).thenReturn(Optional.of(customerPassword));
    // Act
    boolean login = customerService.login(new LoginRequest("username", "password1"));
    // Assert
    Assertions.assertFalse(login);
  }
}
