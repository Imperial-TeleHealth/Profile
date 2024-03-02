package doc.ic.profile;

public record DeleteCustomerRequest(String email, String jwt) implements ProfileRequest{

  @Override
  public String password() {
    return null;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public String dateOfBirth() {
    return null;
  }
}
