package com.kasun.securaty;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kasun.datas.Home;
import com.kasun.datas.Persion;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Security {

    private static final Logger log = LoggerFactory.getLogger(Security.class);
    //private Document document;
    Cipher ecipher;
    Cipher dcipher;
    private final String secretKey = "ezeon8547";

    public Security() {

    }

    byte[] salt = {
        (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    int iterationCount = 19;

    public Persion encryptPersion(Persion persion) throws Exception {

        Persion encryptedPersion = new Persion();

        encryptedPersion.setId(encrypt(persion.getId()));
        encryptedPersion.setName(encrypt(persion.getName()));
        encryptedPersion.setSex(encrypt(persion.getSex()));
        encryptedPersion.setAddress(encrypt(persion.getAddress()));
        encryptedPersion.setTpnum(encrypt(persion.getTpnum()));
        encryptedPersion.setBirthday(encrypt(persion.getBirthday()));
        encryptedPersion.setHomeNumber(encrypt(persion.getHomeNumber()));
        log.info("Persion Encrypted");
        return encryptedPersion;
    }

    public Persion decryptPersion(Persion persion) throws Exception {

        Persion decryptedPersion = new Persion();

        decryptedPersion.setId(decrypt(persion.getId()));
        decryptedPersion.setName(decrypt(persion.getName()));
        decryptedPersion.setSex(decrypt(persion.getSex()));
        decryptedPersion.setAddress(decrypt(persion.getAddress()));
        decryptedPersion.setTpnum(decrypt(persion.getTpnum()));
        decryptedPersion.setBirthday(decrypt(persion.getBirthday()));
        decryptedPersion.setHomeNumber(decrypt(persion.getHomeNumber()));
        log.info("Persion Decrypted");
        return decryptedPersion;
    }

    public Home encryptHome(Home home) throws Exception {

        Home encryptedHome = new Home();

        encryptedHome.setHoemnumber(encrypt(home.getHoemnumber()));
        encryptedHome.setOwner(encrypt(home.getOwner()));
        encryptedHome.setAddress(encrypt(home.getAddress()));
        encryptedHome.setTpnumber(encrypt(home.getTpnumber()));
        encryptedHome.setNumofmembers(home.getNumofmembers());
        log.info("Home Encrypted");
        return encryptedHome;
    }

    public Home decryptHome(Home home) throws Exception {

        Home decryptedHome = new Home();

        decryptedHome.setHoemnumber(decrypt(home.getHoemnumber()));
        decryptedHome.setOwner(decrypt(home.getOwner()));
        decryptedHome.setAddress(decrypt(home.getAddress()));
        decryptedHome.setTpnumber(decrypt(home.getTpnumber()));
        decryptedHome.setNumofmembers(home.getNumofmembers());
        log.info("Home Decrypted");
        return decryptedHome;
    }

    public String encrypt(String plainText) throws Exception {

        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        ecipher = Cipher.getInstance(key.getAlgorithm());
        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        String charSet = "UTF-8";
        byte[] in = plainText.getBytes(charSet);
        byte[] out = ecipher.doFinal(in);
        String encStr = new sun.misc.BASE64Encoder().encode(out);
        return encStr;
    }

    public String decrypt(String encryptedText) throws Exception {

        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        dcipher = Cipher.getInstance(key.getAlgorithm());
        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        byte[] enc = new sun.misc.BASE64Decoder().decodeBuffer(encryptedText);
        byte[] utf8 = dcipher.doFinal(enc);
        String charSet = "UTF-8";
        String plainStr = new String(utf8, charSet);
        return plainStr;
    }
}
