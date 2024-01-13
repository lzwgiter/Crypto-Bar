package core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
     * 输入秘钥：可以为私钥、公钥、对称密钥，具体使用方法取决于密码功能
     */
    @Getter
    private String inputKey;

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
            // 若文件存在则读取
            try {
                // 一次性读入
                Scanner scanner = new Scanner(file).useDelimiter("\\Z");
                this.inputData = scanner.next();
            } catch (FileNotFoundException e) {
                throw new RuntimeException("文件路径不存在！");
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
        File file = new File(cmdLineInputData);
        if (file.exists()) {
            // 文件已存在
            throw new RuntimeException("当前路径已存在该文件！");
        } else {
            File outputFile = new File(cmdLineInputData);
            try {
                // 创建空文件并保存文件名，否则提示已存在该文件
                outputFile.createNewFile();
                this.outputWay = cmdLineInputData;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 设置输入密钥，可以是路径或终端输入
     *
     * @param cmdLineInputData 终端输入参数
     */
    public void setInputKey(String cmdLineInputData) {
        if (cmdLineInputData.contains("/")) {
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
                    throw new RuntimeException("文件路径不存在！");
                }
            } else {
                throw new RuntimeException("请输入文件！");
            }
        } else {
            // 否则使用终端参数作为秘钥
            this.inputKey = cmdLineInputData;
        }
    }
}
