package symmetric;

import core.AgentFactoryAbstract;
import core.AlgoAgentAbstract;
import lombok.Getter;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/17
 */
public class SymmetricAgentFactory extends AgentFactoryAbstract {

    @Getter
    private static final SymmetricAgentFactory instance = new SymmetricAgentFactory();

    private SymmetricAgentFactory() {
    }

    /**
     * 创建算法代理
     *
     * @param algorithm 算法名称
     * @return 算法代理 {@link AlgoAgentAbstract}
     */
    @Override
    public AlgoAgentAbstract createAgent(String algorithm) {
        switch (algorithm) {
            case "AES":
                return new AESAgent();
            case "CHACHA20":
                return new CHACHA20Agent();
            case "SM4":
                return new SM4Agent();
            default:
                return null;
        }
    }
}
