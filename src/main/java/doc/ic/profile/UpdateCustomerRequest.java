package doc.ic.profile;

import java.util.Objects;

public final class UpdateCustomerRequest implements ProfileRequest {
  private final String name;
  private final String dateOfBirth;
  private final String jwt;

  public UpdateCustomerRequest(String name, String dateOfBirth, String jwt) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.jwt = jwt;
  }

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
    return name;
  }

  @Override
  public String dateOfBirth() {
    return dateOfBirth;
  }

  @Override
  public String jwt() {
    return jwt;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (UpdateCustomerRequest) obj;
    return Objects.equals(this.name, that.name)
        && Objects.equals(this.dateOfBirth, that.dateOfBirth)
        && Objects.equals(this.jwt, that.jwt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dateOfBirth, jwt);
  }

  @Override
  public String toString() {
    return "UpdateCustomerRequest["
        + "name="
        + name
        + ", "
        + "dateOfBirth="
        + dateOfBirth
        + ", "
        + "jwt="
        + jwt
        + ']';
  }
}
