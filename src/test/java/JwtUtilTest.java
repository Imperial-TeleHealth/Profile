import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import doc.ic.profile.JwtUtil;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath: application.properties")
@Component
class JwtUtilTest {

  @Value("${dummy_private_key}")
  private String privateKey;

  @Value("${dummy_public_key}")
  private String publicKey;

  //  @Mock
  //  private Claims claims;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    Properties properties = new Properties();
    try (InputStream is =
        getClass().getClassLoader().getResourceAsStream("application.properties")) {
      properties.load(is);
      privateKey = properties.getProperty("dummy_private_key");
      publicKey = properties.getProperty("dummy_public_key");
    } catch (IOException e) {
      // Handle exception
    }
  }

  @Test
  void testGenerateJwtToken() throws NoSuchAlgorithmException, InvalidKeySpecException {
    String email = "test@example.com";
    JwtUtil jwtUtil = JwtUtil.createJwtUtil(privateKey, publicKey);
    String jwtToken = jwtUtil.generateJwtToken(email);
    // Assert that the JWT token is not null
    assertTrue(jwtToken != null && !jwtToken.isEmpty());
  }

  @Test
  void testExtractEmail()
      throws java.security.SignatureException, NoSuchAlgorithmException, InvalidKeySpecException {
    String email = "test@example.com";
    JwtUtil jwtUtil = JwtUtil.createJwtUtil(privateKey, publicKey);
    String jwtToken = jwtUtil.generateJwtToken(email);
    System.out.println(jwtToken);
    String extractedEmail = jwtUtil.extractEmail(jwtToken);

    assertEquals("test@example.com", extractedEmail);
  }

  @Test
  void testExtractEmailInvalidJWT() {
    String jwt = "invalid_jwt_token";
    JwtUtil jwtUtil = JwtUtil.createJwtUtil(privateKey, publicKey);

    assertThrows(java.security.SignatureException.class, () -> jwtUtil.extractEmail(jwt));
  }

  @Test
  void testJwtResponse() throws NoSuchAlgorithmException, InvalidKeySpecException {
    String email = "test@example.com";
    JwtUtil jwtUtil = JwtUtil.createJwtUtil(privateKey, publicKey);

    ResponseEntity<Map<String, Object>> responseEntity = jwtUtil.jwtResponse(email);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    // Assert other properties of the response if needed
  }
}
