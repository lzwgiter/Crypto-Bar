package digest;

import cn.hutool.crypto.SmUtil;
import utils.Utils;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/13
 */
public class SM3Agent extends DigestAgentAbstract {
    @Override
    public String digest(String data) {
        return Utils.getWineHere() + SmUtil.sm3(data);
    }

    @Override
    public String digest(String data, String filePath) {
        Utils.writeToFile(this.digest(data).substring(3), filePath);
        return Utils.getWineHere() + "Result has been written to " + filePath;
    }
}
