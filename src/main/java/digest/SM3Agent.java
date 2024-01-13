package digest;

import cn.hutool.crypto.SmUtil;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/13
 */
public class SM3Agent extends DigestAgentAbstract {
    @Override
    public String digest(String data) {

        return "\uD83C\uDF7A" + SmUtil.sm3(data);
    }
}
