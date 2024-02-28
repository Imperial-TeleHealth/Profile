package doc.ic.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Customerpassword {

  @Id private String username;
  private String password;

  public Customerpassword(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Customerpassword() {}

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customerpassword that = (Customerpassword) o;
    return Objects.equals(username, that.username) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  @Override
  public String toString() {
    return "CustomerPassword{"
        + "username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }
}
