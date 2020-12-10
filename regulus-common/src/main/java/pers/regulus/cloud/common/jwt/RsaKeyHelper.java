package pers.regulus.cloud.common.jwt;

import java.io.DataInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA Key Helper
 *
 * @author Regulus
 */
public class RsaKeyHelper {

    /**
     * 生成RSA公钥
     *
     * @param password
     * @return
     */
    public static byte[] generatePublicKey(String password) throws NoSuchAlgorithmException {
        KeyPairGenerator kpgen = KeyPairGenerator.getInstance("RSA");
        SecureRandom sr = new SecureRandom(password.getBytes());
        kpgen.initialize(1024, sr);
        KeyPair kp = kpgen.generateKeyPair();
        return kp.getPublic().getEncoded();
    }

    /**
     * 生成RSA私钥
     *
     * @param password
     * @return
     */
    public static byte[] generatePrivateKey(String password) throws NoSuchAlgorithmException {
        KeyPairGenerator kpgen = KeyPairGenerator.getInstance("RSA");
        SecureRandom sr = new SecureRandom(password.getBytes());
        kpgen.initialize(1024, sr);
        KeyPair kp = kpgen.generateKeyPair();
        return kp.getPrivate().getEncoded();
    }

    /**
     * 获取公钥
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public PublicKey getPublicKey(String filename) throws Exception {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
        DataInputStream dis = new DataInputStream(is);
        byte[] keyBytes = new byte[is.available()];
        dis.readFully(keyBytes);
        dis.close();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * 获取密钥
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public PrivateKey getPrivateKey(String filename) throws Exception {
        InputStream is = this.getClass().getResourceAsStream(filename);
        DataInputStream dis = new DataInputStream(is);
        byte[] keyBytes = new byte[is.available()];
        dis.readFully(keyBytes);
        dis.close();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public PublicKey getPublicKey(byte[] publicKey) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * 获取密钥
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public PrivateKey getPrivateKey(byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

}
