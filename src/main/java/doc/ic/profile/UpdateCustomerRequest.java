package doc.ic.profile;

public record UpdateCustomerRequest(String name, String dateOfBirth)
    implements ProfileRequest {

  @Override
  public String email() {
    return null;
  }

  @Override
  public String password() {
    return null;
  }

  @Override
  public String jwt() {
    return null;
  }

  @Override
  public String toString() {
    return "UpdateCustomerRequest["
        + "name="
        + name
        + ", "
        + "dateOfBirth="
        + dateOfBirth
        + ']';
  }
}
