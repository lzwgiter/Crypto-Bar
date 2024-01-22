package symmetric;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import core.AlgoContext;
import utils.Utils;

import javax.crypto.spec.SecretKeySpec;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/17
 */
public class SM4Agent extends SymmetricAgentAbstract{
    @Override
    public String encrypt(AlgoContext context) {
        // 根据用于输入推导得到密钥
        SecretKeySpec keyToUse = this.deriveStandardKey(context.getInputKey(), 128);
        // SM4加密
        SymmetricCrypto sm4 = SmUtil.sm4(keyToUse.getEncoded());
        return Utils.getWineHere() + Utils.byteToBase64String(sm4.encrypt(context.getInputData()));
    }

    @Override
    public String decrypt(AlgoContext context) {
        // 解码密文
        byte[] encryptedData = Utils.base64StringToByte(context.getInputData());
        // 根据用户输入推到出合理长度密钥
        SecretKeySpec keyToUse = this.deriveStandardKey(context.getInputKey(), 128);
        SymmetricCrypto sm4 = SmUtil.sm4(keyToUse.getEncoded());
        return Utils.getWineHere() + new String(sm4.decrypt(encryptedData));
    }
}
