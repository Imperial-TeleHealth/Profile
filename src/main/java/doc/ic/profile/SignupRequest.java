package doc.ic.profile;

public record SignupRequest(String email, String password, String name, String dateOfBirth) implements ProfileRequest {

  @Override
  public String jwt() {
    return null;
  }

  @Override
  public String toString() {
    return "SignupRequest[" + "email=" + email + ", " + "password=" + password + ']';
  }
}
