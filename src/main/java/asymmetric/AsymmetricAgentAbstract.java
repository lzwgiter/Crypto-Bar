package asymmetric;

import core.AlgoAgentAbstract;
import core.AlgoContext;

/**
 * 非对称加解密算法抽象代理类
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/13
 */
public abstract class AsymmetricAgentAbstract extends AlgoAgentAbstract {
    @Override
    public String process(AlgoContext context) {
        if (context.isGeneKey()) {
            return this.generateKey(context);
        } else {
            return null;
        }
    }

    /**
     * 生成公私钥对
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String generateKey(AlgoContext context);

    /**
     * 加密
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String encrypt(AlgoContext context);

    /**
     * 解密
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String decrypt(AlgoContext context);

    /**
     * 签名
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String sign(AlgoContext context);

    /**
     * 验签
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String verify(AlgoContext context);
}
