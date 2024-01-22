package symmetric;

import core.AlgoContext;
import utils.Utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/17
 */
public class AESAgent extends SymmetricAgentAbstract {

    /**
     * AES工作模式
     */
    private final String algorithm = "AES/CTR/NoPadding";

    private final int ivLength = 16;

    @Override
    public String encrypt(AlgoContext context) {
        try {
            byte[] dataToBeEncrypted = context.getInputData().getBytes(StandardCharsets.UTF_8);
            // 根据用户输入推到出合理长度密钥
            SecretKeySpec keyToUse = this.deriveStandardKey(context.getInputKey(), 192);

            // 初始化向量参数
            byte[] ivBytes = new byte[ivLength];
            SecureRandom random = SecureRandom.getInstanceStrong();
            random.nextBytes(ivBytes);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, keyToUse, ivParameterSpec);

            // 得到加密数据
            byte[] cipherText = cipher.doFinal(dataToBeEncrypted);

            // 拼接IV和加密后的数据
            byte[] combinedBytes = new byte[ivLength + cipherText.length];
            System.arraycopy(ivBytes, 0, combinedBytes, 0, ivBytes.length);
            System.arraycopy(cipherText, 0, combinedBytes, ivBytes.length, cipherText.length);

            return Utils.getWineHere() + Utils.byteToBase64String(combinedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(AlgoContext context) {
        try {
            // 解码密文
            byte[] encryptedData = Utils.base64StringToByte(context.getInputData());
            // 根据用户输入推到出合理长度密钥
            SecretKeySpec keyToUse = this.deriveStandardKey(context.getInputKey(), 192);

            // 初始化向量参数
            byte[] ivBytes = new byte[ivLength];
            byte[] dataToBeDecrypted = new byte[encryptedData.length - ivLength];
            System.arraycopy(encryptedData, 0, ivBytes, 0, ivBytes.length);
            System.arraycopy(encryptedData, ivBytes.length, dataToBeDecrypted, 0, dataToBeDecrypted.length);

            // 初始化向量参数
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, keyToUse, ivParameterSpec);

            // 得到原始数据
            byte[] plainText = cipher.doFinal(dataToBeDecrypted);

            return Utils.getWineHere() + new String(plainText, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
