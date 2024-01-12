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
        switch (algorithm) {
            case "MD5":
                return new MD5Agent();
            case "SHA-1":
                return new SHA256Agent();
//            case "SHA-256":
//                return new DigestSha256();
//            case "SHA-384":
//                return new DigestSha384();
//            case "SHA-512":
//                return new DigestSha512();
            default:
                return null;
        }
    }
}
