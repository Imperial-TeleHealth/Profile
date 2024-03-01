package doc.ic.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Customerpassword {

  @Id private String email;
  private String password;

  public Customerpassword(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Customerpassword() {}

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
    return Objects.equals(email, that.email) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password);
  }

  @Override
  public String toString() {
    return "CustomerPassword{"
        + "username='"
        + email
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }
}
