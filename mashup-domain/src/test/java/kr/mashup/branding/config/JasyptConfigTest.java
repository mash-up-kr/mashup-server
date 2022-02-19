package kr.mashup.branding.config;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Run > Edit Configurations > Configuration > Environment variables > 'JASYPT_ENCRYPTOR_PASSWORD={암호화키}' 입력
 */
@Disabled("필요할 때만 사용하기 위해 disabled 처리함")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class JasyptConfigTest {

    @Value("${jasypt.encryptor.password}")
    private String jasyptEncryptorPassword;

    @Autowired
    ConfigurableEnvironment configurableEnvironment;

    DefaultLazyEncryptor encryptor;

    @BeforeEach
    void setUp() throws Exception {
        System.out.println(jasyptEncryptorPassword);
        if(StringUtils.isBlank(jasyptEncryptorPassword)) {
            throw new Exception("jasypt.encryptor.password must not be null, empty or blank.");
        }
        encryptor = new DefaultLazyEncryptor(configurableEnvironment);
    }

    @Test
    void testForEncryption() {
        String source = "string want to encrypt";
        System.out.println("source: " + source);
        System.out.println("encrypted: " + encryptor.encrypt(source));
    }

    @Test
    void testForDecryption() {
        // 암호화 되지 않은 문자열을 넣으면 복호화시 에러 발생함
        String source = "string want to decrypt";
        System.out.println("source: " + source);
        System.out.println("encrypted: " + encryptor.decrypt(source));
    }
}