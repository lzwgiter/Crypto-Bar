package utils;

import core.AlgoAgentAbstract;
import core.SupportedAlgo;
import digest.DigestAgentFactory;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/12
 */
public class Utils {
    /**
     * 将byte数组转换为16进制字符串
     *
     * @param bytes byte数组
     * @return 16进制字符串
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            // 4位对应一个十六进制字符。分别取高位和低位添加
            sb.append(Character.forDigit((aByte & 240) >> 4, 16));
            sb.append(Character.forDigit(aByte & 15, 16));
        }
        return sb.toString();
    }

    public static AlgoAgentAbstract getAgentFactory(String algoArg) {
        if (SupportedAlgo.SUPPORTED_DIGEST_ALGO.contains(algoArg)) {
            return DigestAgentFactory.getInstance().createAgent(algoArg);
        }  else {
            return null;
        }
//        else if (SupportedAlgo.SUPPORTED_SYMMETRIC_ALGO.contains(AlgoArg)) {
//
//        } else if (SupportedAlgo.SUPPORTED_ASYMMETRIC_ALGO.contains(AlgoArg)) {
//
//        } else if (SupportedAlgo.SUPPORTED_DSA_ALGO.contains(AlgoArg)) {
//
//        }
    }
}
