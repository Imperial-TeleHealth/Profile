package doc.ic.profile;

public record UpdateCustomerRequest(String email, String name, String dateOfBirth, String jwt)
    implements ProfileRequest {

  @Override
  public String password() {
    return null;
  }
}
