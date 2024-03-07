package doc.ic.profile;

public interface ProfileRequest {
  String email();
  String hashedPassword();
  String name();
  String dateOfBirth();
  String jwt();
}
