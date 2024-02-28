import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import doc.ic.profile.Customer;
import doc.ic.profile.CustomerPasswordRepository;
import doc.ic.profile.CustomerRepository;
import doc.ic.profile.CustomerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


public class CustomerServiceTest {

  @org.mockito.Mock
  public MockitoRule rule = MockitoJUnit.rule();

  private final CustomerRepository customerRepository = mock(CustomerRepository.class);
  private final CustomerPasswordRepository customerPasswordRepository = mock(CustomerPasswordRepository.class);

  private final List<Customer> customersList= new ArrayList<>();

  @Test
  public void getCustomerReturnsListOfCustomers() {
    // Arrange
    customersList.add(new Customer("username", "name", "email", 20));
    CustomerService customerService = new CustomerService(customerRepository, customerPasswordRepository);
    when(customerRepository.findAll()).thenReturn(customersList);
    // Act
    List<Customer> customers = customerService.getCustomer();
    // Assert
    assertEquals(1, customers.size());
  }
}
