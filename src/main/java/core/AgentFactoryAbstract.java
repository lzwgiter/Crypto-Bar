package core;

/**
 * 所有算法代理工厂的抽象类
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/12
 */
public abstract class AgentFactoryAbstract {
    /**
     * 创建算法代理
     *
     * @param algorithm 算法名称
     * @return 算法代理 {@link AlgoAgentAbstract}
     */
    public abstract AlgoAgentAbstract createAgent(String algorithm);
}
