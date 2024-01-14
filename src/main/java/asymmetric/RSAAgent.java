package asymmetric;

import core.AlgoContext;
import utils.Utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/13
 */
public class RSAAgent extends AsymmetricAgentAbstract {
    /**
     * 生成RSA公私钥对，默认为2048 bits长度
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String generateKey(AlgoContext context) {
        try {
            KeyPairGenerator rsaKeysPairGenerator = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥位数
            rsaKeysPairGenerator.initialize(2048);
            // 获取公私钥对
            KeyPair keyPair = rsaKeysPairGenerator.genKeyPair();
            if (context.getOutputWay() != null) {
                Utils.writeToFile(keyPair.getPublic().getFormat(), context.getOutputWay());
                Utils.writeToFile(keyPair.getPrivate().getFormat(), context.getOutputWay());
                return "\uD83C\uDF77：已写入文件" + context.getOutputWay();
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("\uD83C\uDF77：\n");
                sb.append("公钥（X.509格式）：\n-----BEGIN PUBLIC KEY-----\n");
                sb.append(Utils.normalizeKeyFormat(keyPair.getPublic().getEncoded()));
                sb.append("\n-----END PUBLIC KEY-----\n");
                sb.append("私钥（PKCS#8）：\n-----BEGIN PRIVATE KEY-----\n");
                sb.append(Utils.normalizeKeyFormat(keyPair.getPrivate().getEncoded()));
                sb.append("\n-----END PRIVATE KEY-----");
                return sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String encrypt(AlgoContext context) {
        return null;
    }

    /**
     * 解密
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String decrypt(AlgoContext context) {
        return null;
    }

    /**
     * 签名
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String sign(AlgoContext context) {
        return null;
    }

    /**
     * 验签
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String verify(AlgoContext context) {
        return null;
    }
}
