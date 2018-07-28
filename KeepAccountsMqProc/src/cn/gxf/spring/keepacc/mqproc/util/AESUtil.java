package cn.gxf.spring.keepacc.mqproc.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";// Ĭ�ϵļ����㷨
	private static final String KEY = "9f265d42ab3c66d8f50a3a2e793a30c2";
	/**
	 * AES ���ܲ���
	 *
	 * @param content
	 *            ����������
	 * @param password
	 *            ��������
	 * @return ����Base64ת���ļ�������
	 */
	public static String encrypt(String content) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// ����������

			byte[] byteContent = content.getBytes("utf-8");

			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(KEY));// ��ʼ��Ϊ����ģʽ��������

			byte[] result = cipher.doFinal(byteContent);// ����

			return byteToHex(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * AES ���ܲ���
	 *
	 * @param content
	 * @param password
	 * @return
	 */
	public static String decrypt(String content) {

		try {
			// ʵ����
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

			// ʹ����Կ��ʼ��������Ϊ����ģʽ
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(KEY));

			// ִ�в���
			byte[] result = cipher.doFinal(hexToByte(content));

			return new String(result, "utf-8");
		} catch (Exception ex) {
			// Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null,
			// ex);
		}

		return null;
	}

	/**
	 * ���ɼ�����Կ
	 *
	 * @return
	 */
	private static SecretKeySpec getSecretKey(final String password) {
		// ��������ָ���㷨��Կ�������� KeyGenerator ����
		KeyGenerator kg = null;

		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);

			// AES Ҫ����Կ����Ϊ 128
			kg.init(128, new SecureRandom(password.getBytes()));

			// ����һ����Կ
			SecretKey secretKey = kg.generateKey();

			return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// ת��ΪAESר����Կ
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	static final String HEXES = "0123456789ABCDEF";

	public static String byteToHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

	public static byte[] hexToByte(String hexString) {
		int len = hexString.length();
		byte[] ba = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			ba[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));
		}
		return ba;
	}

	public static void main(String[] args) {
		String s = "string_to_encrypt";

		System.out.println("s:" + s);

		String s1 = AESUtil.encrypt(s);
		System.out.println("s1:" + s1);

		System.out.println("s2:" + AESUtil.decrypt(s1));

	}

}
