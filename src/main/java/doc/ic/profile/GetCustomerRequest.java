package doc.ic.profile;

import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (GetCustomerRequest) obj;
    return Objects.equals(this.jwt, that.jwt);
  }

  @Override
  public String toString() {
    return "GetCustomerRequest[" + "jwt=" + jwt + ']';
  }
}
