import core.AlgoAgentAbstract;
import core.AlgoContext;
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
        System.out.println("\033[38;5;14m" +
                """
                           ______                 __              ____           \s
                          / ____/______  ______  / /_____        / __ )____ ______
                         / /   / ___/ / / / __ \\/ __/ __ \\______/ __  / __ `/ ___/
                        / /___/ /  / /_/ / /_/ / /_/ /_/ /_____/ /_/ / /_/ / /   \s
                        \\____/_/   \\__, / .___/\\__/\\____/     /_____/\\__,_/_/    (by lzwgiter v1.0)\s
                                  /____/_/                                       \s
                        \033[0m"""
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
                        .desc("工作模式，加密（e)、解密（d）、签名(s)、验签(v)")
                        .build()
        );
        // 密钥
        options.addOption(
                Option.builder("k")
                        .longOpt("key")
                        .hasArg()
                        .desc("加密/签名、解密/验签密钥文件路径")
                        .build()
        );
        // 签名内容
        options.addOption(
                Option.builder("s")
                        .longOpt("sign")
                        .hasArg()
                        .desc("待校验签名内容")
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
        // bonus
        options.addOption(
                Option.builder("x")
                        .longOpt("bonus")
                        .desc("彩蛋")
                        .build()
        );
        // 创建命令行解析器
        CommandLineParser parser = new DefaultParser();
        CommandLine line;
        try {
            // 解析命令行
            line = parser.parse(options, args);
            if (line.hasOption("x")) {
                System.out.println(Utils.getBonus());
            }
            if (line.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("""
                                java -jar crypto-bar.jar -n <algo_name> [options] <args>\033[38;5;10m
                                  - eg: SM3摘要：java -jar crypto-bar.jar -n sm3 -i "plain text"
                                  - eg: 生成RSA公私钥：java -jar crypto-bar.jar -n rsa -g -o ./output
                                  - eg: 验证RSA签名：java -jar crypto-bar.jar -n rsa -m d -i <原始数据> -s <签名内容> -k <公钥>\033[0m""",
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
            }
            if (!cmdLine.hasOption("n")) {
                System.out.println("请指定要使用的算法！请使用-l查看支持的算法");
            } else {
                // 获取算法上下文和代理对象
                AlgoContext context = AlgoContext.buildAlgoContext(cmdLine);
                AlgoAgentAbstract agent = Utils.getAgentFactory(cmdLine.getOptionValue("n").toUpperCase());
                if (agent != null) {
                    System.out.println(agent.process(context));
                } else {
                    System.out.println("不受支持的算法！请使用-l查看支持的算法");
                }
            }
        }
    }
}
