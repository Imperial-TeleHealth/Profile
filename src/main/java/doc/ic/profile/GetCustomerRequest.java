package doc.ic.profile;

import java.util.Objects;

public final class GetCustomerRequest implements ProfileRequest {
  private final String jwt;

  public GetCustomerRequest(String jwt) {
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
    return null;
  }

  @Override
  public String dateOfBirth() {
    return null;
  }

  @Override
  public String jwt() {
    return jwt;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (GetCustomerRequest) obj;
    return Objects.equals(this.jwt, that.jwt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jwt);
  }

  @Override
  public String toString() {
    return "GetCustomerRequest[" + "jwt=" + jwt + ']';
  }
}
