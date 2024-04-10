package business.encryptor;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

/**
 * Clase que se encarga de encriptar passwords con el algoritmo MD5
 *
 * @author Ricardo Espantaleón
 */
public class Encryptor {

	/**
	 * Función que encripta una contraseña con el algoritmo MD5
	 * 
	 * @param password Contraseña a encriptar
	 * @return Retorna el hash de la contraseña encriptada
	 */
	static public String encryptPassword(String password) {
		String encryptedpassword = null;

		try {
			MessageDigest m = MessageDigest.getInstance("MD5");

			m.update(password.getBytes());

			// Hash del password
			byte[] bytes = m.digest();

			StringBuilder s = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			encryptedpassword = s.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return encryptedpassword;
	}

}
