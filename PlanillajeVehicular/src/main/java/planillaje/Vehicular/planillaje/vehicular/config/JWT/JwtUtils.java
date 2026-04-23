package planillaje.Vehicular.planillaje.vehicular.config.JWT;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;

import javax.crypto.SecretKey;
import javax.sound.midi.SysexMessage;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {
    private static final long ACCESS_EXPIRATION = 900000; // 15 minutos
    private static final long REFRESH_EXPIRATION = 604800000; // 7 días{

    @Value("${jwt.secret:miClaveSecretaPorDefecto1234567891011121314151617}")
    private String secretString;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    //GENERAR ACCESS TOKEN
    public String generarToken(UsuarioEntity usuario) {
        //Obtener el rol del usario
        String rol = usuario.getRoles().getRoleName();

        return Jwts.builder().setSubject(usuario.getUsername()).claim("role", rol).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION)).signWith(getSecretKey(), SignatureAlgorithm.HS256).compact();
    }

    //GENERAR REFRESH TOKEN
    public String refreshToken(UsuarioEntity usuario) {
        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Extraer Subject del token

    public String getSubjectFromToken(String token) {
        try {
            if (token == null || token.equals("null") || token.isEmpty()) return null;
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    //VALIDACIÓN DEL TOKEN
    public boolean validarToken(String token) {
        try {
            if (token == null || token.isBlank()) return false;
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}
