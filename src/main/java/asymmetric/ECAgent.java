package asymmetric;

import core.AlgoContext;

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
        return null;
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
