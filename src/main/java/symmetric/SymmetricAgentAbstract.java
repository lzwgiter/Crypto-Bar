package symmetric;

import core.AlgoAgentAbstract;
import core.AlgoContext;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/17
 */
public abstract class SymmetricAgentAbstract extends AlgoAgentAbstract {

    // 128bytes随机盐值
    private static final byte[] RANDOM_KDF_SALT = {-51, -94, -9, 96, -115, 61, -69, -122, 45, 125, -125, -18, -33,
            119, -12, -17, 69, -94, 97, 94, 77, 98, -14, -27, 97, -2, -58, -20, 43, 0, 109, -73, 113, 13, 126, -117,
            40, -66, -29, 52, -121, 68, -52, 112, -113, -80, 127, -43, -66, 20, 57, 61, -100, -109, 56, 77, 87, -41,
            32, -11, 27, 63, -42, -6, -99, 98, 0, 120, -5, -22, 58, -89, -22, -81, 10, -89, 97, 79, 47, 124, 21, 114,
            -72, 69, 60, -60, 74, -104, 89, -2, 18, 31, 12, 105, -57, 64, -1, -81, -56, -109, -87, 9, -121, -13, -30,
            74, -79, 24, -53, -98, -93, -90, -108, 9, -66, -74, 46, 48, -111, -121, 50, -48, -117, 74, 66, -98, -30};


    /**
     * 处理输入，操作包括加密、解密、签名、验签、摘要、验证摘要
     *
     * @param context 算法上下文
     * @return 返回结果
     */
    @Override
    public String process(AlgoContext context) {
        if ("e".equals(context.getMode())) {
            return encrypt(context);
        } else if ("d".equals(context.getMode())) {
            return decrypt(context);
        } else {
            return "Wrong working mode! using -h for usage.";
        }
    }

    /**
     * 密钥派生函数，根据用户输入的秘密值推导出加密密钥
     *
     * @param secret 用户输入的秘密值
     * @return {@link SecretKeySpec}
     */
    protected SecretKeySpec deriveStandardKey(String secret, int keyLength) {
        // KDF派生出的AES秘钥长度，这里默认采用128bits，否则为192bits
        int kdfKeyByteLength = keyLength == 128 ? 16 : 24;
        // KDF算法迭代次数
        int pbkdf2Iterations = 10000;
        try {

            // 推导生成对称加密要使用的密钥
            KeySpec keySpec = new PBEKeySpec(secret.toCharArray(), RANDOM_KDF_SALT,
                    pbkdf2Iterations, kdfKeyByteLength * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对给定数据进行对称加密
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String encrypt(AlgoContext context);

    /**
     * 对给定加密数据进行解密
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String decrypt(AlgoContext context);
}
