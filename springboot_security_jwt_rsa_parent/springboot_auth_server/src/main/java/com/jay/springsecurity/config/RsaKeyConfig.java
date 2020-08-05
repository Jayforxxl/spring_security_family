package com.jay.springsecurity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import springboot.common.utils.RsaUtils;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Description:
 *
 * @author ZhangJieChao
 * @version 1.0
 * @date 2020/8/1 14:43
 */

@ConfigurationProperties("rsa.key") //读取配置文件中的配置项
public class RsaKeyConfig {

    private String privateFilePath;//私钥
    private String publicFilePath;//公钥

    //真正使用到的公钥秘钥对象
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct//在对象构造完成之后执行
    public void createRsaKey() throws Exception {
        this.privateKey = RsaUtils.getPrivateKey(privateFilePath);
        this.publicKey = RsaUtils.getPublicKey(publicFilePath);
    }

    public String getPrivateFilePath() {
        return privateFilePath;
    }

    public void setPrivateFilePath(String privateFilePath) {
        this.privateFilePath = privateFilePath;
    }

    public String getPublicFilePath() {
        return publicFilePath;
    }

    public void setPublicFilePath(String publicFilePath) {
        this.publicFilePath = publicFilePath;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
