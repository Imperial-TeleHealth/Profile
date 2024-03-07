package doc.ic.profile;

public record SignupRequest(String email, String hashedPassword, String name, String dateOfBirth) implements ProfileRequest {

  @Override
  public String jwt() {
    return null;
  }

  @Override
  public String toString() {
    return "SignupRequest{" +
        "email='" + email + '\'' +
        ", password='" + hashedPassword + '\'' +
        ", name='" + name + '\'' +
        ", dateOfBirth='" + dateOfBirth + '\'' +
        '}';
  }
}
