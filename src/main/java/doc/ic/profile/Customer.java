package doc.ic.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Customer {

  @Id private String username;

  private String name;
  private String email;
  private Integer age;

  public Customer(String username, String name, String email, Integer age) {
    this.username = username;
    this.name = name;
    this.email = email;
    this.age = age;
  }

  public Customer() {}

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return Objects.equals(username, customer.username)
        && Objects.equals(name, customer.name)
        && Objects.equals(email, customer.email)
        && Objects.equals(age, customer.age);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, name, email, age);
  }

  @Override
  public String toString() {
    return "Customer{"
        + "id="
        + username
        + ", name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + ", age="
        + age
        + '}';
  }
}
