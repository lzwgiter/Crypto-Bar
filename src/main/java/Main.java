import core.AlgoAgentAbstract;
import core.AlgoContext;
import org.apache.commons.cli.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import utils.Utils;

import java.security.Security;

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
                           "   ______                 __              ____            \n" +
                           "  / ____/______  ______  / /_____        / __ )____ ______\n" +
                           " / /   / ___/ / / / __ \\/ __/ __ \\______/ __  / __ `/ ___/\n" +
                           "/ /___/ /  / /_/ / /_/ / /_/ /_/ /_____/ /_/ / /_/ / /    \n" +
                           "\\____/_/   \\__, / .___/\\__/\\____/     /_____/\\__,_/_/    (by lzwgiter v1.1) \n" +
                           "          /____/_/                                        \n" +
                           "\033[0m"
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
                .desc("show this message")
                .build());
        // 支持算法
        options.addOption(Option.builder("l")
                .longOpt("list")
                .desc("Supported algorithms")
                .build());
        // 密钥生成算法
        options.addOption(Option.builder("g")
                .longOpt("genKey")
                .argName("keyType")
                .desc("Generate key pair")
                .build());
        // 算法名称
        options.addOption(
                Option.builder("n")
                        .longOpt("algo")
                        .argName("algo")
                        .hasArg()
                        .desc("Algorithm name, using -l for supported algorithms")
                        .build()
        );
        // 工作模式
        options.addOption(
                Option.builder("m")
                        .longOpt("mode")
                        .hasArg()
                        .argName("mode")
                        .desc("Work mode, encrypt(e) | decrypt(d) | signature(s) | verify(v)")
                        .build()
        );
        // 密钥
        options.addOption(
                Option.builder("k")
                        .longOpt("key")
                        .hasArg()
                        .desc("Key file path")
                        .build()
        );
        // 签名内容
        options.addOption(
                Option.builder("s")
                        .longOpt("sign")
                        .hasArg()
                        .desc("Signature to be verified")
                        .build()
        );
        // 输入内容
        options.addOption(
                Option.builder("i")
                        .longOpt("input")
                        .hasArg()
                        .argName("input")
                        .desc("Input data")
                        .build()
        );
        // 输出到文件
        options.addOption(
                Option.builder("o")
                        .longOpt("output")
                        .hasArg()
                        .argName("output")
                        .desc("Output file path")
                        .build()
        );
        // bonus
        options.addOption(
                Option.builder("x")
                        .longOpt("bonus")
                        .desc("Bonus")
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
                System.exit(0);
            }
            if (line.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar crypto-bar.jar -n <algo_name> [options] <args>\n\033[38;5;10m" +
                                    "- eg: generate SM3 hash: xxx -n sm3 -i <data>\n" +
                                    "- eg: generate RSA key pair: xxx -n rsa -g -o ./output\n" +
                                    "- eg: Verify a Signature: xxx -n rsa -m d -i <original data> -s <signature> -k <pubkey>\n" +
                                    "- eg: En/Decrypt: xxx -n sm4 -m e[d] -i <data to be en/decrypt> -k <secret>\033[0m",
                        options);
                System.exit(0);
            } else {
                return line;
            }
        } catch (ParseException exp) {
            System.out.println("parameter parse failed, using -h for usage.");
        }
        return null;
    }

    public static void main(String[] args) {
        banner();
        Security.addProvider(new BouncyCastleProvider());
        CommandLine cmdLine = parseArgs(args);
        if (cmdLine != null) {
            if (cmdLine.hasOption("l")) {
                System.out.println("Symmetric: SM4(128bits), AES(192bits)");
                System.out.println("Asymmetric: SM2, RSA(2048bits)");
                System.out.println("Signature: ECC(Ed25519)");
                System.out.println("Hash: SM3, SHA256, MD5");
                System.exit(0);
            }
            if (!cmdLine.hasOption("n")) {
                System.out.println("Algorithm name is needed, using -l for supported algorithms");
            } else {
                // 获取算法上下文和代理对象
                AlgoContext context = AlgoContext.buildAlgoContext(cmdLine);
                AlgoAgentAbstract agent = Utils.getAgentFactory(cmdLine.getOptionValue("n").toUpperCase());
                if (agent != null) {
                    System.out.println(agent.process(context));
                } else {
                    System.out.println("unsupported algorithm, using -l for supported algorithms");
                }
            }
        }
    }
}
