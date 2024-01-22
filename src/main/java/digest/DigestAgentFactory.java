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
        if ("MD5".equals(algorithm)) {
            return new MD5Agent();
        } if ("SHA256".equals(algorithm)) {
            return new SHA256Agent();
        } else if ("SM3".equals(algorithm)) {
            return new SM3Agent();
        } else {
            return null;
        }
    }
}
