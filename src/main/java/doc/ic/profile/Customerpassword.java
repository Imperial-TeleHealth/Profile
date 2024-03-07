package doc.ic.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Customerpassword {

  @Id private String email;
  private String hashedPassword;

  public Customerpassword(String email, String hashedPassword) {
    this.email = email;
    this.hashedPassword = hashedPassword;
  }

  public Customerpassword() {}

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String password) {
    this.hashedPassword = password;
  }

  public void setEmail(String username) {
    this.email = username;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customerpassword that = (Customerpassword) o;
    return Objects.equals(email, that.email) && Objects.equals(hashedPassword, that.hashedPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, hashedPassword);
  }

  @Override
  public String toString() {
    return "CustomerPassword{"
        + "username='"
        + email
        + '\''
        + ", password='"
        + hashedPassword
        + '\''
        + '}';
  }
}
