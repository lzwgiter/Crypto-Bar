package digest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import utils.Utils;

/**
 * MD5消息摘要类
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/12
 */
public class MD5Agent extends DigestAgentAbstract {
    /**
     * 计算摘要
     *
     * @param data 待计算摘要数据
     * @return 输出长度为16字节的摘要
     */
    @Override
    public String digest(String data) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md5.update(data.getBytes(StandardCharsets.UTF_8));
        return Utils.getWineHere() + Utils.byteToHexString(md5.digest());
    }

    @Override
    public String digest(String data, String filePath) {
        Utils.writeToFile(this.digest(data).substring(3), filePath);
        return Utils.getWineHere() + "结果已经写入" + filePath;
    }
}
