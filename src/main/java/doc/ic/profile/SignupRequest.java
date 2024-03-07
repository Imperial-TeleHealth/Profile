package doc.ic.profile;

import java.util.Objects;

public record SignupRequest(String email, String password) implements ProfileRequest {

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
    return "SignupRequest[" + "email=" + email + ", " + "password=" + password + ']';
  }
}
