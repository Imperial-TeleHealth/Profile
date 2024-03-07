package doc.ic.profile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${JWT_KEY}")
  private String PRIVATE_KEY;

  @Value("${JWT_PUB_KEY}")
  private String PUBLIC_KEY;

  private JwtUtil(String privateKey, String publicKey) {
    this.PRIVATE_KEY = privateKey;
    this.PUBLIC_KEY = publicKey;
  }

  public static JwtUtil createJwtUtil(String privateKey, String publicKey) {
    return new JwtUtil(privateKey, publicKey);
  }

  public String generateJwtToken(String email)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] keyBytes = Base64.getDecoder().decode(PRIVATE_KEY);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

    return Jwts.builder()
        .setSubject("email")
        .setIssuer("daniel")
        .claim("email", email)
        .signWith(privateKey, SignatureAlgorithm.RS256)
        .compact();
  }

  public String extractEmail(String jwt) throws SignatureException {
    String[] parts = jwt.split("\\.");
    String encodedPayload = parts[1];

    // Decode the payload part
    Base64.Decoder decoder = Base64.getDecoder();
    byte[] decodedBytes = decoder.decode(encodedPayload);
    String payloadJson = new String(decodedBytes);

    // Parse the payload JSON string
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode payloadNode = objectMapper.readTree(payloadJson);
      return payloadNode.get("email").asText();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Map<String, Object>> jwtResponse(String email)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    String jwtToken = generateJwtToken(email);
    Map<String, Object> response = new HashMap<>();
    response.put("ok", true);
    response.put("token", jwtToken);
    response.put("public_key", PUBLIC_KEY);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
