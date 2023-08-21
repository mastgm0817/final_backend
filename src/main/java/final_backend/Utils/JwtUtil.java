package final_backend.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String getNickName(String token, String secretKey ){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("nickName", String.class);

    }

    public static String getEmail(String token, String secretKey ){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email", String.class);

    }

    public static boolean isExpired(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public static boolean validateRefreshToken(String refreshToken, String secretKey) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateRefreshToken(String email, String secretKey, Long expired) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expired))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//    public static String createJwt(String email, String nickName, String secretKey, Long expired){
//        Claims claims = Jwts.claims();
//        claims.put("email", email);
//        claims.put("nickName", nickName);
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expired))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//Refresh Token

    public static String createJwt(String email, String nickName, String secretKey, Long expired){

        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("nickName", nickName);
        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expired))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return accessToken;
    }

    public static String createRefreshJwt(String email, String nickName, String secretKey, Long expired){
        Date now = new Date();

        Claims claims = Jwts.claims();
        String refreshToken =  Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + expired*60)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();


        return refreshToken;
    }
}
