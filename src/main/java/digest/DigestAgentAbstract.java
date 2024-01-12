package digest;

import core.AlgoAgentAbstract;

/**
 * 消息摘要抽象类
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/12
 */
public abstract class DigestAgentAbstract extends AlgoAgentAbstract {
    /**
     * 处理输入数据
     *
     * @param input 输入数据
     * @return 消息摘要
     */
    @Override
    public String process(String input) {
        return this.digest(input);
    }

    /**
     * 计算消息摘要
     * @param data 输入数据
     * @return 消息摘要
     */
    public abstract String digest(String data);
}
