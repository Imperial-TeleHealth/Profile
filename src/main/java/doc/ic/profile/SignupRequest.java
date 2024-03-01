package doc.ic.profile;

public record SignupRequest(String email, String password) implements ProfileRequest {

  @Override
  public String name() {
    return null;
  }

  @Override
  public String dateOfBirth() {
    return null;
  }
}