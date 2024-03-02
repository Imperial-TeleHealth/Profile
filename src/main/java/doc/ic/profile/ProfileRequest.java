package doc.ic.profile;

public interface ProfileRequest {
  public String email();
  public String password();
  public String name();
  public String dateOfBirth();

  public String jwt();
}
