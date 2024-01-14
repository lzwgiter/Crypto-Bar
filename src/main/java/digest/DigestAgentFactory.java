package digest;

import core.AgentFactoryAbstract;
import lombok.Getter;

/**
 * 消息摘要工厂
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/12
 */
public class DigestAgentFactory extends AgentFactoryAbstract {

    @Getter
    private static final DigestAgentFactory instance = new DigestAgentFactory();

    private DigestAgentFactory() {}

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
