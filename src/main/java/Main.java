import org.apache.commons.cli.*;

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

    public static void main(String[] args) {
        banner();
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
        try {
            // 解析命令行
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java crypto-bar.jar [args]",
                        options);
            }
            if (line.hasOption("l")) {
                System.out.println(
                        """
                        对称加密算法：SM4、AES、CHACHA20
                        非对称加密/签名算法：SM2、RSA、ECC（secp256k1）
                        哈希摘要算法：SM3、SHA512、MD5
                        """
                );
            }
        } catch (ParseException exp) {
            System.err.println("参数解析错误：" + exp.getMessage());
        }

    }
}
