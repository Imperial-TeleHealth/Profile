package doc.ic.profile;

import java.util.List;

public record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person) {}
