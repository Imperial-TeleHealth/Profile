package doc.ic.profile;

public interface ProfileRequest {
  String email();
  String password();
  String name();
  String dateOfBirth();
  String jwt();
}
