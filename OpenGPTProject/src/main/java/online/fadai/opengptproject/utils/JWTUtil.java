package online.fadai.opengptproject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTUtil {

    //加密算法
    private final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    //私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
    // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
    private final static String secret = "031027031027031027031027";

    /**
     * 创建jwt
     *
     * @return 返回生成的jwt token
     */
    public static String generateJwtToken(String username, String email) {

        // 头部 map / Jwt的头部承载，第一部分
        // 可不设置 默认格式是{"alg":"HS256"}
        Map<String, Object> map = new HashMap<>();
        map.put("alg", SIGNATURE_ALGORITHM.getValue());
        map.put("typ", "JWT");

        Map<String, Object> claims = new HashMap<>();

        claims.put("username", username);
        claims.put("email", email);


        return Jwts.builder()
                .setHeader(map)
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    /**
     * 从jwt中获取 载荷 信息
     */
    public static Claims getClaimsFromJwt(String jwt) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return claims;
    }
}
