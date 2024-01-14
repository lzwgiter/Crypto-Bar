package utils;

import asymmetric.AsymmetricAgentFactory;
import core.AlgoAgentAbstract;
import core.SupportedAlgo;
import digest.DigestAgentFactory;

import java.io.*;
import java.util.Base64;

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

    /**
     *  将byte数组转换为Base64字符串
     * @param bytes 待编码数据
     * @return Base64字符串
     */
    public static String byteToBase64String(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 格式化密钥字符串为70字符一行
     * @param keyBytes 原始密钥字节字符串
     * @return 格式化的密钥base64字符串
     */
    public static String normalizeKeyFormat(byte[] keyBytes) {
        String base64KeyString = byteToBase64String(keyBytes);
        StringBuilder sb = new StringBuilder();
        int rowAccount = base64KeyString.length() / 70;
        for (int i = 0; i < rowAccount; i++) {
            sb.append(base64KeyString, i * 70, (i + 1) * 70).append("\n");
        }
        sb.append(base64KeyString, rowAccount * 70, base64KeyString.length() - 1);
        return sb.toString();
    }

    /**
     * 将算法结果写入文件
     * @param data 算法输出
     * @param fileName 文件名称
     */
    public static void writeToFile(String data, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 获取算法代理工厂实例
     *
     * @param algoArg 算法名称参数
     * @return {@link AlgoAgentAbstract}
     */
    public static AlgoAgentAbstract getAgentFactory(String algoArg) {
        if (SupportedAlgo.SUPPORTED_DIGEST_ALGO.contains(algoArg)) {
            return DigestAgentFactory.getInstance().createAgent(algoArg);
        } else if (SupportedAlgo.SUPPORTED_ASYMMETRIC_ALGO.contains(algoArg)) {
            return AsymmetricAgentFactory.getInstance().createAgent(algoArg);
        }
        else {
            return null;
        }
//         else if (SupportedAlgo.SUPPORTED_ASYMMETRIC_ALGO.contains(AlgoArg)) {
//
//        } else if (SupportedAlgo.SUPPORTED_DSA_ALGO.contains(AlgoArg)) {
//
//        }
    }
}
