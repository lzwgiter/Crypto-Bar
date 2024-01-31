package core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.cli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 算法上下文，包含终端输入相关信息
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/13
 */
@Getter
@NoArgsConstructor
public class AlgoContext {
    /**
     * 算法输入数据
     */
    private String inputData;

    /**
     * 输出方式：终端、文件
     */
    private String outputWay;

    /**
     * 是否使用生成公私钥功能
     */
    @Setter
    private boolean geneKey;

    /**
     * 工作模式：加密/签名（e)、解密/验签（d）
     */
    @Setter
    private String mode;

    /**
     * 输入密钥：可以为私钥、公钥、对称密钥，具体使用方法取决于密码功能
     */
    @Getter
    private String inputKey;

    @Getter
    private String signature;

    /**
     * 设置输入数据，文件或终端
     *
     * @param cmdLineInputData 终端输入参数
     */
    public void setInputData(String cmdLineInputData) {
        // 输入为文件
        if (cmdLineInputData.contains("/")) {
            // 文件路径
            File file = new File(cmdLineInputData);
            if (file.isFile()) {
                try {
                    // 一次性读入
                    Scanner scanner = new Scanner(file).useDelimiter("\\Z");
                    this.inputData = scanner.next();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("File path not exists!");
                }
            } else {
                // 输入数据为包含"/"的字符串
                this.inputData = cmdLineInputData;
            }
        } else {
            // 输入来自终端参数
            this.inputData = cmdLineInputData;
        }
    }

    /**
     * 设置输出文件路径
     *
     * @param cmdLineInputData 终端输入参数
     */
    public void setOutputWay(String cmdLineInputData) {
        // 输出为文件
        // 文件路径
        File outputFile = new File(cmdLineInputData);
        // 若文件存在则提示已存在该文件
        if (!outputFile.exists()) {
            this.outputWay = cmdLineInputData;
        } else {
            throw new RuntimeException("File already exists!");
        }
    }

    /**
     * 设置输入密钥，可以是路径或终端输入
     *
     * @param cmdLineInputData 终端输入参数
     */
    public void setInputKey(String cmdLineInputData) {
        if (cmdLineInputData.contains("/") || cmdLineInputData.contains("\\")) {
            // 文件路径
            File file = new File(cmdLineInputData);
            // 若文件存在则读取
            if (file.isFile()) {
                // 若文件存在则读取
                try {
                    // 一次性读入
                    Scanner scanner = new Scanner(file).useDelimiter("\\Z");
                    this.inputKey = scanner.next();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("File path not exists!");
                }
            } else {
                throw new RuntimeException("Input is a directory, using -i to input file, using -h for usage");
            }
        } else {
            // 否则使用终端参数作为密钥
            this.inputKey = cmdLineInputData;
        }
    }

    public void setSignature(String cmdLineInputData) {
        if (cmdLineInputData.contains("/") || cmdLineInputData.contains("\\")) {
            // 文件路径
            File file = new File(cmdLineInputData);
            // 若文件存在则读取
            if (file.isFile()) {
                // 若文件存在则读取
                try {
                    // 一次性读入
                    Scanner scanner = new Scanner(file).useDelimiter("\\Z");
                    this.signature = scanner.next();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("File path not exists!");
                }
            } else {
                this.signature = cmdLineInputData;
            }
        } else {
            // 否则使用终端参数作为输入签名内容
            this.signature = cmdLineInputData;
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
                context.setMode(cmdLine.getOptionValue("m"));
                // 加解密功能
                if (cmdLine.hasOption("k")) {
                    context.setInputKey(cmdLine.getOptionValue("k"));
                    if (cmdLine.hasOption("s")) {
                        // 签名验证功能
                        context.setSignature(cmdLine.getOptionValue("s"));
                    }
                } else {
                    throw new RuntimeException("key is needed, using -k to input key. using -h for usage");
                }
            }
        }
        return context;
    }
}
