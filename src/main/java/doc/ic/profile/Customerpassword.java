package doc.ic.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Customerpassword {

  @Id private String email;
  private String hashPassword;

  public Customerpassword(String email, String hashPassword) {
    this.email = email;
    this.hashPassword = hashPassword;
  }

  public Customerpassword() {}

  public String getHashPassword() {
    return hashPassword;
  }

  public void setHashPassword(String password) {
    this.hashPassword = password;
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
    return Objects.equals(email, that.email) && Objects.equals(hashPassword, that.hashPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, hashPassword);
  }

  @Override
  public String toString() {
    return "CustomerPassword{"
        + "username='"
        + email
        + '\''
        + ", password='"
        + hashPassword
        + '\''
        + '}';
  }
}
