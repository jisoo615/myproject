package com.example.myproject;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JasyptConfigTest {
    /**
     * 현재 jasypt와 spring boot 버전 호환이 되지 않아
     * ENC() 내의 문자열 해독 불가 -> 버전 업그레이 되면 적용하기
     * 조치: .gitignore 파일에 application-dev.yml을 등록
     */
    @Test
    public void jasyptTest(){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword("환경변수에 저장된 JASYPT_PASSWORD 값(jasypt의 password)");// 암호화 복호화에 필요한 키
        config.setAlgorithm("PBEWithMD5AndTripleDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);

        String plainText = "암호화 되어야 하는 값";
        String encryptedText = encryptor.encrypt(plainText);
        String decryptedText = encryptor.decrypt(encryptedText);

        System.out.println("Enc: " + encryptedText);
        System.out.println("Dec: " + decryptedText);
        assertThat(plainText).isEqualTo(decryptedText);
    }

}
