package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 支持算法列表
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since ${DATE}
 */
public class SupportedAlgo {
    /**
     * 支持的消息摘要算法
     */
    public static final List<String> SUPPORTED_DIGEST_ALGO = new ArrayList<>(Arrays.asList("SM3", "SHA256", "MD5"));

    /**
     * 支持的对称加密算法
     */
    public static final List<String> SUPPORTED_SYMMETRIC_ALGO = new ArrayList<>(Arrays.asList("SM4", "AES", "CHACHA20"));

    /**
     * 支持的非对称加密算法
     */
    public static final List<String> SUPPORTED_ASYMMETRIC_ALGO = new ArrayList<>(Arrays.asList("SM2", "RSA", "ECC"));

}
