package doc.ic.profile;

public record GetCustomerRequest(String jwt) implements ProfileRequest {

  @Override
  public String email() {
    return null;
  }

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
