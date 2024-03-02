package doc.ic.profile;

public record UpdateCustomerRequest(String name, String dateOfBirth, String jwt)
    implements ProfileRequest {

  @Override
  public String email() {
    return null;
  }

  @Override
  public String password() {
    return null;
  }
}
