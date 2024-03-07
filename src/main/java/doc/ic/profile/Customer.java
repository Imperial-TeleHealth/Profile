package doc.ic.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Customer {

  @Id private String email;
  private String name;
  private String dateOfBirth;
  private Boolean isDoctor;

  public Customer(String email, String name, String dateOfBirth, Boolean isDoctor) {
    this.email = email;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.isDoctor = isDoctor;
  }

  public Customer() {}

  public String getEmail() {
    return email;
  }

  public void setEmail(String username) {
    this.email = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Boolean getIsDoctor() {
    return isDoctor;
  }

  public void setDoctor(Boolean doctor) {
    isDoctor = doctor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return Objects.equals(email, customer.email)
        && Objects.equals(name, customer.name)
        && Objects.equals(dateOfBirth, customer.dateOfBirth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, name, dateOfBirth);
  }

  @Override
  public String toString() {
    return "Customer{" + "id=" + email + ", name='" + name + '\'' + ", age=" + dateOfBirth + '}';
  }
}
