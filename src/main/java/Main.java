import core.AlgoAgentAbstract;
import org.apache.commons.cli.*;
import utils.Utils;

/**
 * 主程序。包含logo和参数解析
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/12
 */
public class Main {
    public static void banner() {
        System.out.println(
                """
                           ______                 __              ____           \s
                          / ____/______  ______  / /_____        / __ )____ ______
                         / /   / ___/ / / / __ \\/ __/ __ \\______/ __  / __ `/ ___/
                        / /___/ /  / /_/ / /_/ / /_/ /_/ /_____/ /_/ / /_/ / /   \s
                        \\____/_/   \\__, / .___/\\__/\\____/     /_____/\\__,_/_/    \s
                                  /____/_/                                       \s
                        """
        );
    }

    private static CommandLine parseArgs(String[] args) {
        if (args.length == 0) {
            args = new String[]{"-h"};
        }
        Options options = new Options();

        // 帮助选项
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("显示帮助信息")
                .build());
        // 支持算法
        options.addOption(Option.builder("l")
                .longOpt("list")
                .desc("查看支持算法")
                .build());
        // 密钥生成算法
        options.addOption(Option.builder("g")
                .longOpt("genKey")
                .argName("keyType")
                .hasArg()
                .desc("生成PEM格式公私钥对（RSA2048、ECC）")
                .build());
        // 算法名称
        options.addOption(
                Option.builder("n")
                        .longOpt("algo")
                        .argName("algo")
                        .hasArg()
                        .desc("算法名称，例如AES。-l查看所有支持算法。")
                        .build()
        );
        // 工作模式
        options.addOption(
                Option.builder("m")
                        .longOpt("mode")
                        .hasArg()
                        .argName("mode")
                        .desc("工作模式，加密/签名（e)、解密/验签（d）")
                        .build()
        );
        // 密钥
        options.addOption(
                Option.builder("k")
                        .longOpt("key")
                        .hasArg()
                        .argName("key")
                        .desc("加密/签名、解密/验签密钥PEM文件路径")
                        .build()
        );
        // 输入内容
        options.addOption(
                Option.builder("i")
                        .longOpt("input")
                        .hasArg()
                        .argName("input")
                        .desc("输入内容")
                        .build()
        );
        // 输出到文件
        options.addOption(
                Option.builder("o")
                        .longOpt("output")
                        .hasArg()
                        .argName("output")
                        .desc("输出文件路径")
                        .build()
        );
        // 创建命令行解析器
        CommandLineParser parser = new DefaultParser();
        CommandLine line;
        try {
            // 解析命令行
            line = parser.parse(options, args);
            if (line.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java crypto-bar.jar [args]",
                        options);
            } else {
                return line;
            }
        } catch (ParseException exp) {
            System.out.println("参数解析错误，请使用 -h 查看帮助信息。");
        }
        return null;
    }

    public static void main(String[] args) {
        banner();
        CommandLine cmdLine = parseArgs(args);
        if (cmdLine != null) {
            if (cmdLine.hasOption("l")) {
                System.out.println(
                        """
                                对称加密算法：SM4、AES、CHACHA20
                                非对称加密/签名算法：SM2、RSA、ECC（secp256k1）
                                哈希摘要算法：SM3、SHA256、MD5
                                """
                );
            } else {
                AlgoAgentAbstract agent = Utils.getAgentFactory(cmdLine.getOptionValue("n"));
                if (agent != null) {
                    System.out.println(agent.process(cmdLine.getOptionValue("i")));
                } else {
                    System.out.println("不受支持的算法！请使用-l查看支持的算法");
                }

            }
        }
    }
}
