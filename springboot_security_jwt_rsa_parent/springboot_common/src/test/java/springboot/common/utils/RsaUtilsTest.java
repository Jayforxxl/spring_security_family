package springboot.common.utils;


import org.junit.Test;

/**
 * Description:
 *
 * @author ZhangJieChao
 * @version 1.0
 * @date 2020/8/1 13:36
 */
public class RsaUtilsTest {

    //定义生成公钥和私钥的地址
    private String privateFilePath = "/Users/Jay/Desktop/auth_key/id_key_rsa";
    private String publicFilePath = "/Users/Jay/Desktop/auth_key/id_key_rsa_pub";

    /**
     * 生成公钥和私钥
     * @throws Exception
     */
    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFilePath,privateFilePath,"salt",2048);
    }

    @Test
    public void getPublicKey() throws Exception {
        RsaUtils.getPublicKey(publicFilePath);
    }

    @Test
    public void getPrivateKey() throws Exception {
        RsaUtils.getPrivateKey(privateFilePath);
    }

}
