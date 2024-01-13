package utils;

import core.AlgoAgentAbstract;
import core.AlgoContext;
import core.SupportedAlgo;
import digest.DigestAgentFactory;
import org.apache.commons.cli.CommandLine;

import java.io.*;

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
     * 解析命令行参数并创建算法上下文
     * @param cmdLine 命令行参数
     * @return {@link AlgoContext}
     */
    public static AlgoContext buildAlgoContext(CommandLine cmdLine) {
        AlgoContext context = new AlgoContext();
        // 设置输入数据
        if (cmdLine.hasOption("i")) {
            context.setInputData(cmdLine.getOptionValue("i"));
        }
        // 设置输出方式
        if (cmdLine.hasOption("o")) {
            context.setOutputWay(cmdLine.getOptionValue("o"));
        }
        // 是否生成公私钥对
        if (cmdLine.hasOption("g")) {
            context.setGeneKey(true);
            // 使用生成公私钥功能，直接返回
            return context;
        } else {
            // 非生成公私钥对功能，即加解密、摘要功能
            if (cmdLine.hasOption("m")) {
                // 加解密功能
                if (cmdLine.hasOption("k")) {
                    context.setInputKey(cmdLine.getOptionValue("k"));
                } else {
                    throw new RuntimeException("请给出对称/非对称秘钥！");
                }
            }
        }
        return context;
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
        } else {
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
