package digest;

import utils.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/12
 */
public class SHA256Agent extends DigestAgentAbstract {
    @Override
    public String digest(String data) {
        MessageDigest sha256;
        try {
            sha256 = MessageDigest.getInstance("SHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        sha256.update(data.getBytes(StandardCharsets.UTF_8));
        return Utils.getWineHere() + Utils.byteToHexString(sha256.digest());
    }

    @Override
    public String digest(String data, String filePath) {
        Utils.writeToFile(this.digest(data).substring(3), filePath);
        return Utils.getWineHere() + "Result has been written to " + filePath;
    }
}
