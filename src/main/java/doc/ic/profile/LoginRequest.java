package doc.ic.profile;

import java.util.Objects;

public final class LoginRequest implements ProfileRequest {
  private final String email;
  private final String password;

  public LoginRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

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
  public String email() {
    return email;
  }

  @Override
  public String password() {
    return password;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (LoginRequest) obj;
    return Objects.equals(this.email, that.email) && Objects.equals(this.password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password);
  }

  @Override
  public String toString() {
    return "LoginRequest[" + "email=" + email + ", " + "password=" + password + ']';
  }
}
