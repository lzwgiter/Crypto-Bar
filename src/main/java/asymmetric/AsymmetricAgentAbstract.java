package asymmetric;

import cn.hutool.crypto.SmUtil;
import core.AlgoAgentAbstract;
import core.AlgoContext;
import utils.Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 非对称加解密算法抽象代理类
 *
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/13
 */
public abstract class AsymmetricAgentAbstract extends AlgoAgentAbstract {
    @Override
    public String process(AlgoContext context) {
        // 生成公私钥功能
        if (context.isGeneKey()) {
            return this.generateKey(context);
        }
        if (context.getMode() == null) {
            throw new RuntimeException("请指定工作模式！");
        }
        // 否则为加解密功能
        return switch (context.getMode()) {
            case "e" -> this.encrypt(context);
            case "d" -> this.decrypt(context);
            case "s" -> this.sign(context);
            case "v" -> this.verify(context);
            default -> null;
        };
    }

    /**
     * 生成公私钥，可以选择写入文件或输出到终端
     *
     * @param keyPairGenerator 密钥生成器
     * @param writeToFile      是否写入文件
     * @return String
     */
    protected String generateKey(KeyPairGenerator keyPairGenerator, boolean writeToFile, String filePath) {
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        if (writeToFile) {
            Utils.writeToFile(Utils.byteToBase64String(keyPair.getPublic().getEncoded()),
                    filePath + ".pub");
            Utils.writeToFile(Utils.byteToBase64String(keyPair.getPrivate().getEncoded()),
                    filePath + ".pri");
            return "\uD83C\uDF77：已写入文件" + filePath;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(Utils.getAWineHere());
            sb.append("\033[38;5;10m公钥（X.509格式）：\033[0m\n-----BEGIN PUBLIC KEY-----\n");
            sb.append(Utils.normalizeFormat(keyPair.getPublic().getEncoded()));
            sb.append("\n-----END PUBLIC KEY-----\n");
            sb.append("\033[38;5;10m私钥（PKCS#8）：\033[0m\n-----BEGIN PRIVATE KEY-----\n");
            sb.append(Utils.normalizeFormat(keyPair.getPrivate().getEncoded()));
            sb.append("\n-----END PRIVATE KEY-----");
            return sb.toString();
        }
    }

    /**
     * 生成公私钥对
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String generateKey(AlgoContext context);

    /**
     * 利用指定算法和公钥对给定数据进行加密操作
     * @param algoName 算法名称
     * @param key 公钥 {@link PublicKey}
     * @param dataToEncrypt 待加密数据
     * @return String
     */
    protected String encrypt(String algoName, String key, String dataToEncrypt) {
        byte[] encryptKeyBytes = Utils.base64StringToByte(key);

        try {
            PublicKey publicKey = KeyFactory.getInstance(algoName)
                    .generatePublic(new X509EncodedKeySpec(encryptKeyBytes));
            Cipher cipher = Cipher.getInstance(algoName);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(dataToEncrypt.getBytes(StandardCharsets.UTF_8));
            return Utils.getAWineHere() + Utils.byteToBase64String(result);
        } catch (NoSuchPaddingException | InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String encrypt(AlgoContext context);

    /**
     * 利用指定算法和私钥对给定数据进行解密操作
     * @param algoName 算法名称
     * @param key 私钥 {@link PrivateKey}
     * @param dataToDecrypt 待加密数据
     * @return String
     */
    protected String decrypt(String algoName, String key, String dataToDecrypt) {
        byte[] encryptKeyBytes = Utils.base64StringToByte(key);
        try {
            PrivateKey privateKey = KeyFactory.getInstance(algoName)
                    .generatePrivate(new PKCS8EncodedKeySpec(encryptKeyBytes));
            Cipher cipher = Cipher.getInstance(algoName);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(Utils.base64StringToByte(dataToDecrypt));
            return Utils.getAWineHere() + new String(result);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param context 算法上下文
     * @return String
     */
    public abstract String decrypt(AlgoContext context);

    /**
     * 数字签名
     * @param algoName 算法名称
     * @param signKey 签名私钥 {@link PrivateKey}
     * @param dataToSign 待签名数据
     * @return String
     */
    protected String sign(String algoName, String signKey, String dataToSign) {
        byte[] signKeyBytes = Utils.base64StringToByte(signKey);
        String dataHash = SmUtil.sm3(dataToSign);
        try {
            PrivateKey privateKey = KeyFactory.getInstance(algoName)
                    .generatePrivate(new PKCS8EncodedKeySpec(signKeyBytes));
            Cipher cipher = Cipher.getInstance(algoName);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(dataHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            sb.append(Utils.getAWineHere());
            sb.append("原始数据：");
            sb.append(dataToSign);
            sb.append("\n签名数据：");
            sb.append(Utils.byteToBase64String(result));
            return sb.toString();
        } catch (NoSuchPaddingException | InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 签名
     * @param context 算法上下文
     * @return String
     */
    public abstract String sign(AlgoContext context);


    protected String verify(String algoName, String verifyKey, String signToVerifybase64String, String originalData) {
        byte[] signKeyBytes = Utils.base64StringToByte(verifyKey);
        byte[] signToVerify = Utils.base64StringToByte(signToVerifybase64String);
        String originalDataHash = SmUtil.sm3(originalData);
        try {
            PublicKey publicKey = KeyFactory.getInstance(algoName)
                    .generatePublic(new X509EncodedKeySpec(signKeyBytes));
            Cipher cipher = Cipher.getInstance(algoName);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(signToVerify);
            if (SmUtil.sm3(new String(result)).equals(originalDataHash)) {
                return Utils.getAWineHere() + "签名验证通过！";
            } else {
                return Utils.getAWineHere() + "签名验证失败！";
            }
        } catch (NoSuchPaddingException | InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 签名验证
     * @param context 算法上下文
     * @return String
     */
    public abstract String verify(AlgoContext context);
}
