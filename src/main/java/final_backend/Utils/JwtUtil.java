package final_backend.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String getNickName(String token, String secretKey ){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("nickName", String.class);

    }

    public static boolean isExpired(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public static String createJwt(String nickName, String secretKey, Long expired){
        Claims claims = Jwts.claims();
        claims.put("nickName", nickName);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expired))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}