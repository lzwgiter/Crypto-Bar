package asymmetric;

import core.AlgoContext;
import java.security.*;

/**
 * 椭圆曲线体制代理
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/13
 */
public class ECAgent extends AsymmetricAgentAbstract {

    /**
     * 生成公私钥对
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String generateKey(AlgoContext context) {
        try {
            KeyPairGenerator ecKeysPairGenerator = KeyPairGenerator.getInstance("Ed25519");
            // 获取公私钥对
            if (context.getOutputWay() != null) {
                return this.generateKey(ecKeysPairGenerator, true, context.getOutputWay());
            } else {
                return this.generateKey(ecKeysPairGenerator, false, null);
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
        // todo 椭圆曲线算法貌似这里不支持直接加密
//        return this.encrypt("Ed25519", context.getInputKey(), context.getInputData());
        return "Not Supported";
    }

    /**
     * 解密
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String decrypt(AlgoContext context) {
        // todo 解密一样
//        return this.decrypt("Ed25519", context.getInputKey(), context.getInputData());
         return "Not Supported";
    }

    @Override
    public String sign(AlgoContext context) {
        return this.sign("Ed25519", context.getInputKey(), context.getInputData());
    }

    @Override
    public String verify(AlgoContext context) {
        return this.verify("Ed25519", context.getInputKey(), context.getSignature(), context.getInputData());
    }
}
