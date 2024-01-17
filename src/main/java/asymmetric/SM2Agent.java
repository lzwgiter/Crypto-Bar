package asymmetric;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import core.AlgoContext;
import utils.Utils;

import java.security.KeyPair;

/**
 * @author lzwgiter
 * @email float311@163.com
 * @since 2024/1/13
 */
public class SM2Agent extends AsymmetricAgentAbstract {

    /**
     * 生成公私钥对
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String generateKey(AlgoContext context) {
        KeyPair keyPair = SecureUtil.generateKeyPair("SM2");
        if (context.getOutputWay() != null) {
            Utils.writeToFile(Utils.byteToBase64String(keyPair.getPublic().getEncoded()),
                    context.getOutputWay() + ".pub");
            Utils.writeToFile(Utils.byteToBase64String(keyPair.getPrivate().getEncoded()),
                    context.getOutputWay() + ".pri");
            return "\uD83C\uDF77：已写入文件" + context.getOutputWay();
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
     * 加密
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String encrypt(AlgoContext context) {
        SM2 sm2 = new SM2();
        sm2.setPublicKey(SecureUtil.generatePublicKey("SM2", Utils.base64StringToByte(context.getInputKey())));
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.getAWineHere());
        sb.append(Utils.byteToBase64String(sm2.encrypt(context.getInputData(), KeyType.PublicKey)));
        return sb.toString();
    }

    /**
     * 解密
     *
     * @param context 算法上下文
     * @return String
     */
    @Override
    public String decrypt(AlgoContext context) {
        SM2 sm2 = new SM2();
        sm2.setPrivateKey(SecureUtil.generatePrivateKey("SM2", Utils.base64StringToByte(context.getInputKey())));
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.getAWineHere());
        sb.append(new String(sm2.decrypt(context.getInputData(), KeyType.PrivateKey)));
        return sb.toString();
    }

    @Override
    public String sign(AlgoContext context) {
        return null;
    }

    @Override
    public String verify(AlgoContext context) {
        return null;
    }
}
