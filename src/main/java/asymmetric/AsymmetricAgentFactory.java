package asymmetric;

import core.AgentFactoryAbstract;
import core.AlgoAgentAbstract;
import lombok.Getter;

/**
 * 非对称算法代理对象工厂
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/13
 */
public class AsymmetricAgentFactory extends AgentFactoryAbstract {

    @Getter
    private static final AsymmetricAgentFactory instance = new AsymmetricAgentFactory();

    private AsymmetricAgentFactory() {}

    @Override
    public AlgoAgentAbstract createAgent(String algorithm) {
        return switch (algorithm) {
            case "RSA" -> new RSAAgent();
            case "ECC" -> new ECAgent();
            case "SM2" -> new SM2Agent();
            default -> null;
        };
    }
}
