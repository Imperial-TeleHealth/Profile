import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import doc.ic.profile.Customer;
import doc.ic.profile.CustomerPasswordRepository;
import doc.ic.profile.CustomerRepository;
import doc.ic.profile.CustomerService;
import doc.ic.profile.Customerpassword;
import doc.ic.profile.DeleteCustomerRequest;
import doc.ic.profile.GetCustomerRequest;
import doc.ic.profile.JwtUtil;
import doc.ic.profile.LoginRequest;
import doc.ic.profile.SignupRequest;
import doc.ic.profile.UpdateCustomerRequest;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

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
    GetCustomerRequest request = new GetCustomerRequest("jwt");
    when(jwtUtil.extractEmail(request.jwt())).thenReturn("email");
    when(customerRepository.findById("email")).thenReturn(Optional.of(customer));

    // Act
    Customer result = customerService.getCustomer(request);

    // Assert
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result, customer);
  }

  @Test
  public void getCustomerReturnsNullIfJwtThrowException() throws SignatureException {
    // Arrange
    GetCustomerRequest request = new GetCustomerRequest("jwt");
    when(jwtUtil.extractEmail(request.jwt())).thenThrow(SignatureException.class);

    // Assert
    Assertions.assertThrows(
        SignatureException.class,
        () -> {
          customerService.getCustomer(request);
        });
  }

  @Test
  public void deleteCustomerDeletesCustomers() throws SignatureException {
    // Arrange
    DeleteCustomerRequest request = new DeleteCustomerRequest("jwt");
    when(jwtUtil.extractEmail(request.jwt())).thenReturn("email");

    // Act
    customerService.deleteCustomer(request);
    // Assert
    Mockito.verify(customerRepository, times(1)).deleteById("email");
  }

  @Test
  public void updateCustomerUpdatesCustomers() throws SignatureException {
    // Arrange
    Customer updatedCustomer = new Customer("email", "name", "02/03/2024");
    UpdateCustomerRequest request = new UpdateCustomerRequest("name", "02/03/2024", "jwt");
    when(jwtUtil.extractEmail(request.jwt())).thenReturn("email");
    when(customerRepository.findById("email")).thenReturn(Optional.of(updatedCustomer));
    // Act
    customerService.updateCustomer(request);
    // Assert
    Assertions.assertEquals("name", updatedCustomer.getName());
    Assertions.assertEquals("02/03/2024", updatedCustomer.getDateOfBirth());
  }

  @Test
  public void signupSavesCustomerPassword() {
    // Arrange
    // Act
    customerService.signup(new SignupRequest("email", "password"));
    // Assert
    Mockito.verify(customerPasswordRepository, times(1)).save(customerPassword);
  }

  @Test
  public void loginReturnsTrueIfCustomerPasswordMatches() {
    // Arrange
    when(customerPasswordRepository.findById("email"))
        .thenReturn(java.util.Optional.of(customerPassword));
    // Act
    boolean login = customerService.login(new LoginRequest("email", "password"));
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
