package cn.valinaa.auction.utils;

import lombok.NoArgsConstructor;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.lang.JoseException;

import java.util.UUID;

/**
 * @author Valinaa
 * @Date : 2022/7/17
 * @Description : 生成 keyId与公钥秘钥
 */
@NoArgsConstructor
public class GenerateKeyPair {
    
    public static RsaJsonWebKey rsaJsonWebKey;
    
    /**
     * 创建jwk keyId , 公钥 ，秘钥
     */
    public static void createKeyPair() {
        String keyId = UUID.randomUUID().toString().replaceAll("-", "");
        RsaJsonWebKey jwk = null;
        try {
            jwk = RsaJwkGenerator.generateJwk(2048);
        } catch (JoseException e) {
            e.printStackTrace();
        }
        assert jwk != null;
        jwk.setKeyId(keyId);
        //采用的签名算法 RS256
        jwk.setAlgorithm(AlgorithmIdentifiers.RSA_USING_SHA256);
        String publicKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        String privateKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
        System.out.println("keyId=" + keyId);
        System.out.println();
        System.out.println("公钥 publicKeyStr=" + publicKey);
        System.out.println();
        System.out.println("私钥 privateKeyStr=" + privateKey);
    }
    
    
    /**
     * 公钥密钥生成方法2
     *
     * @return {@link RsaJsonWebKey}
     * @see RsaJsonWebKey
     */
    public static RsaJsonWebKey getInstance() {
        // 生成一个RSA密钥对，用于签署和验证JWT，包装在JWK中
        if (rsaJsonWebKey == null) {
            try {
                rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
                rsaJsonWebKey.setKeyId("jwt1");
            } catch (JoseException e) {
                e.printStackTrace();
            }
        }
        // 给JWK一个关键ID（kid），这是礼貌的做法
        return rsaJsonWebKey;
    }

/*    public static void main(String[] args) {
//            createKeyPair();
        JwtUtil jwt = new JwtUtil();
        String token = jwt.createToken("李雷");
        System.out.println(token);
        String username = jwt.verifyToken(token);
        System.out.println("username:" + username);
    }*/
}
