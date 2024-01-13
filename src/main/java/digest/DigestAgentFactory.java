package digest;

import core.AgentFactoryAbstract;

/**
 * 消息摘要工厂
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/12
 */
public class DigestAgentFactory extends AgentFactoryAbstract {

    private static final DigestAgentFactory FACTORY_INSTANCE = new DigestAgentFactory();

    private DigestAgentFactory() {}

    public static DigestAgentFactory getInstance() {
        return FACTORY_INSTANCE;
    }

    @Override
    public DigestAgentAbstract createAgent(String algorithm) {
        return switch (algorithm) {
            case "MD5" -> new MD5Agent();
            case "SHA256" -> new SHA256Agent();
            case "SM3" -> new SM3Agent();
            default -> null;
        };
    }
}
