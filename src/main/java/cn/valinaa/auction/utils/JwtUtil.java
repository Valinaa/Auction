package cn.valinaa.auction.utils;

import cn.valinaa.manage.entity.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;

/**
 * @author Valinaa
 * @Date : 2022/7/17
 * @Description : Jwt生成类
 */
@Component
public class JwtUtil {
    
    // public static RsaJsonWebKey rsaJsonWebKey = GenerateKeyPair.getInstance();
    // 生成一个RSA密钥对，用于签署和验证JWT，包装在JWK中
    
    private static final String KEY_ID = "ecb9dd7c5bf641b68169f088e09ce2bd";
    private static final String PUBLIC_KEY_STR = "{\"kty\":\"RSA\",\"kid\":\"ecb9dd7c5bf641b68169f088e09ce2bd\",\"alg\":\"RS256\",\"n\":\"sFDcDekYEHTw1L3FR-jTPmv3hDmIdv7NOUQ_QlhciwT1MqOk6tHeRfX7wMX_cUjhMCiRdjjQyQEuLyRAfYT9Jd7IufixaoUD4vXO8bcHCWlgQNFXVXdCXHV7Bo-YqMaDXCngmNTY1RQVljyviLpal__t5bC4d-QrC5VdM9ODxT8iX6Kh_4hUzqfQVM3YcRmdcE2EKxveLc8H5s2s5SeedrSRqhtQddzLZdYG2i48QBe69z_Bqh6snBdC_u_L-XCAdt7fwh__XqhZyGojJ3Or0cQv7r69o6zUdf-9NUkb2s8f3BmPmVD2PPywvHGNSDEVRJopKOKPLIFD5fRGwalnfQ\",\"e\":\"AQAB\"}";
    private static final String PRIVATE_KEY_STR = "{\"kty\":\"RSA\",\"kid\":\"ecb9dd7c5bf641b68169f088e09ce2bd\",\"alg\":\"RS256\",\"n\":\"sFDcDekYEHTw1L3FR-jTPmv3hDmIdv7NOUQ_QlhciwT1MqOk6tHeRfX7wMX_cUjhMCiRdjjQyQEuLyRAfYT9Jd7IufixaoUD4vXO8bcHCWlgQNFXVXdCXHV7Bo-YqMaDXCngmNTY1RQVljyviLpal__t5bC4d-QrC5VdM9ODxT8iX6Kh_4hUzqfQVM3YcRmdcE2EKxveLc8H5s2s5SeedrSRqhtQddzLZdYG2i48QBe69z_Bqh6snBdC_u_L-XCAdt7fwh__XqhZyGojJ3Or0cQv7r69o6zUdf-9NUkb2s8f3BmPmVD2PPywvHGNSDEVRJopKOKPLIFD5fRGwalnfQ\",\"e\":\"AQAB\",\"d\":\"UbxyfxpfdwWXPbBp2--xuj7YBFqdijiOD-20-781y5a1Ss_1HIzFI56oolHHnQ9BoY80-97T31LmQ6FlKis0JdG1MQ16xdw7FISKdqC4KONKZVIG0A6U24L00PJRvXAlplydi4wkpwcECN1CLLrjU5KoCPKXWbETSW_pQKTd8dqRPzuiwSF_CRWpHy9J4-oaTS4pdKGEuNKJ4WLIKwPObYcH8dXmi9dtziyjBF7Tlj35ieag2HeaKsdDlSQ1O7l-KYuYFRDFw20F4mIG_PownMKJUT8AGWuCj1tabjASoQXoQEkbq2gs6WBdcY0MJgdT1jnv3LyMVt0Mv_bHNtqwew\",\"p\":\"z7VJFoA_oY2mesrtxWwo_f7NAc0xrOhS2ryDjVXPqMzLC9JwA-yYOR6xbJZHapAjiy_mhT2wMpPbaKcwSWyoKjE7vLpNWiVxgVO_fxf3J-ojv6-R91VO6-bemmkktf29tGwBGM-5ylilcU-0bpplexKWXym0wBqB8oX4PpuB4ps\",\"q\":\"2U8dxh6_UYBJ07jQZ7nZnyrJG6qFiwDPO0itXLbzeUPkfzDsvNjCuhhOeZJUYpRBqKYrSTa05-GN0u7H3IImqjJK9K5WiI5THfFpppv_95WDKCZmNXK3aVd85hleO4eUdhl-Ek0JOKlROpBFo547fH0I9TAcS16uQ_btOKhOU8c\",\"dp\":\"llMGxYEmDD8fWMlbUkGYXVM2ffJK3G_73xEGSJjtRKaK4VB4_24RMW3UTNlqcGZHQyn0tUPTLJez-MbffJRmFLszF8Dh0Lc-sbGx60Kl_r5HBykDBDU6fCLHwosMJaax0E1GcfWM6jRD8IEV_eJCRdNK_y6JAWtt1t88rdsQIrc\",\"dq\":\"bOsKsVKX8fGrD0OCsXqlaPG4-vTtGk69j6lNuzq3rgApdta-0aiTcVLdgnGlegN1JWKkL34MDm0u-JpV8mAJoSS0DQ-Ur70mS8vEeI2YLi_yHR2h_QgSheoj3_C3Gn9KGieM2P9jr9Kts_y76tZUWoERDFOg2uYJ0mbhX5R8LeE\",\"qi\":\"s8hoZPZ5qQnNNk9lFbpvnqmgrFVEvCnjGYxKwVC5GOfoHGIqQBKcaL7iPV9M8rm8yDtdeOlmAbbh1cuupXd9ciYCKaApWNiIzc4m6yfxeStP7G43pUKabc7A1-0CvWUHG1HJ7IgUh3Ncpv8VKxrjegaldDNomhr9rA31mynPFEc\"}";
    public static long accessTokenExpirationTime = 60 * 60 * 24;
    
    /**
     * jws创建token
     *
     * @param userDetails userDetails
     * @return {@link String}
     * @see String
     */
    public String createToken(UserDetails userDetails) {
        try {
            //Payload
            JwtClaims claims = new JwtClaims();
            // 令牌的唯一标识符
            claims.setGeneratedJwtId();
            // 当令牌被发布/创建时（现在）
            claims.setIssuedAtToNow();
            //expire time
            NumericDate date = NumericDate.now();
            date.addSeconds(accessTokenExpirationTime);
            claims.setExpirationTime(date);
            // 在此之前，令牌无效（2分钟前）
            claims.setNotBeforeMinutesInThePast(2);
            // 令牌失效的时间长（从现在开始20分钟）
            claims.setExpirationTimeMinutesInTheFuture(20);
            // 谁创建了令牌并签署了它
            claims.setIssuer("Valinaa");
            // 主题 ,是令牌的对象
            claims.setSubject(userDetails.getUsername());
            // 令牌将被发送给谁
            claims.setAudience(String.valueOf(userDetails.getAuthorities()));
            //添加自定义参数或附加属性,必须是字符串类型
            claims.setClaim("verifyToken", Result.success());
            claims.setClaim("username", userDetails.getUsername());
            
            // JWT是一个JWS和/或一个带有JSON声明的JWE作为有效负载。
            // 在这个例子中，它是一个JWS，所以我们创建一个JsonWebSignature对象。
            JsonWebSignature jws = new JsonWebSignature();
            // 在jw/jws上设置签名算法RS256，该算法将完整性保护声明
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            // 设置关键ID（kid）头，因为这是一种礼貌的做法。 在这个例子中，我们只有一个键但是使用键ID可以帮助 促进平稳的关键滚动过程
            jws.setKeyIdHeaderValue(KEY_ID);
            // JWS的有效负载是JWT声明的JSON内容
            jws.setPayload(claims.toJson());
            // JWT使用私钥签署
            PrivateKey privateKey = new RsaJsonWebKey(JsonUtil.parseJson(PRIVATE_KEY_STR)).getPrivateKey();
            jws.setKey(privateKey);
            
            /*
             * 签署JWS并生成紧凑的序列化或完整的jw/JWS 表示，它是由三个点（'.'）分隔的字符串
             * 在表单头.payload.签名中使用base64url编码的部件 如果你想对它进行加密，你可以简单地将这个jwt设置为有效负载
             * 在JsonWebEncryption对象中，并将cty（内容类型）头设置为“jwt”。
             */
            return jws.getCompactSerialization();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Token Create Failed!";
    }
    
    /**
     * jws校验token<br/>
     * 返回用户账号
     *
     * @param token       token
     * @param userDetails userDetails
     * @return {@link String}
     * @throws MalformedClaimException org.jose4j.jwt. malformed claim exception
     * @throws JoseException           org.jose4j.lang. jose exception
     * @see String
     */
    public String verifyToken(String token, UserDetails userDetails) throws MalformedClaimException, JoseException {
        
        /*
         * 使用JwtConsumer builder构建适当的JwtConsumer，它将 用于验证和处理JWT。 JWT的具体验证需求是上下文相关的
         * 但通常建议需要一个（合理的）过期时间，一个受信任的时间 发行人, 以及将你的系统定义为预期接收者的受众。
         * 如果JWT也被加密，您只需要提供一个解密密钥对构建器进行解密密钥解析器。
         */
        JwtConsumer consumer = new JwtConsumerBuilder()
                // 必须有一个有效期时间
                .setRequireExpirationTime()
                // .setMaxFutureValidityInMinutes(5256000)
                // 允许在验证基于时间的令牌时留有一定的余地，以计算时钟偏差。单位/秒
                .setAllowedClockSkewInSeconds(30)
                // 主题声明
                .setRequireSubject()
                // JWT需要由谁来发布,用来验证 发布人
                .setExpectedIssuer("Valinaa")
                .setExpectedSubject(userDetails.getUsername())
                // JWT的目的是给谁, 用来验证观众
                .setExpectedAudience(String.valueOf(userDetails.getAuthorities()))
                .setEvaluationTime(NumericDate.now())
                // 只允许在给定上下文中预期的签名算法,使用指定的算法验证
                .setJwsAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                        AlgorithmIdentifiers.RSA_USING_SHA256))
                // 用公钥验证签名 ,验证秘钥
                .setVerificationKey(new RsaJsonWebKey(JsonUtil.parseJson(PUBLIC_KEY_STR)).getPublicKey())
                .build();
        try {
            // 验证JWT并将其处理为jwtClaims
            JwtClaims claims = consumer.processToClaims(token);
            if (claims != null) {
                System.out.println("认证通过！");
                String verifyInfo = new ObjectMapper().writeValueAsString(claims.getClaimValue("verifyToken"));
                String username = (String) claims.getClaimValue("username");
                System.out.println("token payload携带的自定义内容:Jwt验证是否成功？ =>" + verifyInfo);
                System.out.println("token payload携带的自定义内容:携带该token的用户名 =>" + username);
                System.out.println("Jwt Succeed! The JwtClaims:" + claims);
                return username;
            }
        } catch (InvalidJwtException e) {
            System.out.println("Jwt Invalid!" + e);
            // 对JWT无效的（某些）特定原因的编程访问也是可能的
            // 在某些情况下，您是否需要不同的错误处理行为。
            
            // JWT是否已经过期是无效的一个常见原因
            if (e.hasExpired()) {
                System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
            }
            // 或者观众是无效的
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
                System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
    public String getUsernameByToken(String token) throws InvalidJwtException, MalformedClaimException {
        JwtConsumer consumer = new JwtConsumerBuilder().build();
        JwtClaims claims = consumer.processToClaims(token);
        return claims.getSubject();
    }
}
