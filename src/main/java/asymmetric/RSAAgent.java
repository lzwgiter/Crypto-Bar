package asymmetric;

import core.AlgoContext;

import java.security.*;

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
            if (context.getOutputWay() != null) {
                return this.generateKey(rsaKeysPairGenerator, true, context.getOutputWay());
            } else {
                return this.generateKey(rsaKeysPairGenerator, false, null);
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
        return this.encrypt("RSA", context.getInputKey(), context.getInputData());
    }

    /**
     * 解密
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String decrypt(AlgoContext context) {
        return this.decrypt("RSA", context.getInputKey(), context.getInputData());
    }

    @Override
    public String sign(AlgoContext context) {
        return this.sign("RSA", context.getInputKey(), context.getInputData());
    }

    @Override
    public String verify(AlgoContext context) {
        return this.verify("RSA", context.getInputKey(), context.getSignature(), context.getInputData());
    }
}
