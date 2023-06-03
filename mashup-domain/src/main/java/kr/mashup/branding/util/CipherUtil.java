package kr.mashup.branding.util;

import kr.mashup.branding.domain.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
@Slf4j
public class CipherUtil {


    private static final String AES_128 = "AES";

    public static byte[] decryptAES128(
        final String encryptedKey,
        final byte[] symmetricKey){

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedKey);

        SecretKeySpec secretKeySpec = new SecretKeySpec(symmetricKey, AES_128);

        final byte[] decryptedKey;
        try{
            final Cipher cipher = Cipher.getInstance(AES_128);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            decryptedKey = cipher.doFinal(encryptedBytes);
        } catch (Exception e){
            throw new BadRequestException();
        }
        return decryptedKey;
    }

    private CipherUtil() {
    }
}
