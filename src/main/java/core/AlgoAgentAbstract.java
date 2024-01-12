package core;

/**
 * 所有的算法实现代理抽象类
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/12
 */
public abstract class AlgoAgentAbstract {
    /**
     * 处理输入，操作包括加密、解密、签名、验签、摘要、验证摘要
     *
     * @param input 输入数据
     * @return 返回结果
     */
    public abstract String process(String input);
}
