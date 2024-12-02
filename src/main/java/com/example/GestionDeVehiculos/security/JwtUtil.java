package com.example.GestionDeVehiculos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Este componente de utilidad (JwtUtil) maneja la creación, validación y extracción de información
// de los JWT en tu aplicación. Utiliza la clave secreta configurada en el archivo de propiedades
// y maneja operaciones como la firma, validación de expiración y extracción de claims.

@Component
public class JwtUtil {
    //secret: Es la clave secreta utilizada para firmar el JWT. Se obtiene del archivo de
    // configuración de Spring (application.properties).
    @Value("${jwt.secret}")
    private String secret;
    //expiration: El tiempo de expiración del token,
    //también configurado en el archivo de propiedades.
    @Value("${jwt.expiration}")
    private long expiration;
    /*
    * Utiliza la clave secreta para crear un SecretKey que se usará para firmar el JWT.
    * El método Keys.hmacShaKeyFor es utilizado para generar la clave secreta en formato HMAC-SHA.
    * */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //NOTA:Ambos métodos utilizan extractClaim, un
    // método genérico que extrae cualquier tipo de información del token.

    //extractUsername: Extrae el nombre de usuario (que es el "subject" del token).
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //extractExpiration: Extrae la fecha de expiración del token.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
    * extractClaim: Es un método genérico que permite extraer
    * cualquier "claim" (información) del JWT, como el nombre de usuario, fecha de expiración, etc.
    * Utiliza un claimsResolver para decidir qué parte del Claims se quiere extraer.
    * Por ejemplo, Claims::getSubject para obtener el nombre de usuario.
    * */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    /*
    *
    * extractAllClaims: Este método se encarga de parsear el token JWT usando la clave secreta (getSigningKey()) y obtener
    * todos los "claims" del token. parseClaimsJws verifica la validez del token y lo decodifica.
    * */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //isTokenExpired: Verifica si el token ha expirado comparando la fecha de expiración con la fecha actual.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //generateToken: Genera un token JWT para un usuario dado, usando el nombre de
    // usuario (userDetails.getUsername()) y un mapa vacío de "claims". El método createToken se encarga de crear el JWT.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    //    createToken: Construye el JWT usando la librería Jwts.builder().
    //    setClaims: Establece los claims del token (en este caso, un mapa vacío).
    //    setSubject: Establece el "subject" del token (el nombre de usuario).
    //    setIssuedAt: Establece la fecha en la que el token fue emitido.
    //    setExpiration: Establece la fecha de expiración.
    //            signWith: Firma el token usando la clave secreta.
    //            compact: Devuelve el token como una cadena compacta.
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }
    //    validateToken: Valida el token comprobando que:
    //    El nombre de usuario extraído del token coincida con el nombre de usuario del UserDetails proporcionado.
    //    El token no haya expirado.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}