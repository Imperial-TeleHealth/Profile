package doc.ic.profile;

public record LoginRequest(String email, String password) implements ProfileRequest {

  @Override
  public String name() {
    return null;
  }

  @Override
  public String dateOfBirth() {
    return null;
  }

  @Override
  public String jwt() {
    return null;
  }

  @Override
  public String toString() {
    return "LoginRequest[" + "email=" + email + ", " + "password=" + password + ']';
  }
}
