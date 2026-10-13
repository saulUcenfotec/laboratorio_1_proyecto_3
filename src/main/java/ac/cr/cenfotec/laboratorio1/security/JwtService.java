package ac.cr.cenfotec.laboratorio1.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class JwtService {
    private final SecretKey key;
    private final long expirationMs;
    /** Prepara la clave de firma y el tiempo de vida configurado para los tokens. */
    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-ms}") long expirationMs) { this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expirationMs = expirationMs; }
    /** Firma un JWT con el usuario autenticado y las fechas de emisión y expiración. */
    public String generate(Authentication auth) { Date now = new Date(); return Jwts.builder().subject(auth.getName()).issuedAt(now).expiration(new Date(now.getTime() + expirationMs)).signWith(key).compact(); }
    /** Valida firma y vigencia del JWT y extrae el nombre de usuario. */
    public String username(String token) { return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject(); }
}
