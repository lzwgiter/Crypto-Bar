package digest;

import core.AlgoAgentAbstract;
import core.AlgoContext;

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
     * @param context 算法上下文
     * @return 消息摘要
     */
    @Override
    public String process(AlgoContext context) {
        if (context.getOutputWay() != null) {
            return this.digest(context.getInputData(), context.getOutputWay());
        } else {
            return this.digest(context.getInputData());
        }
    }

    /**
     * 计算消息摘要
     *
     * @param data 输入数据
     * @return 消息摘要
     */
    public abstract String digest(String data);

    /**
     * 计算消息摘要并写入文件
     *
     * @param data     输入数据
     * @param filePath 输出文件路径
     * @return String
     */
    public abstract String digest(String data, String filePath);
}
