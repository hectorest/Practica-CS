import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.*;


public class AES {

	// VARIABLES DE INSTANCIA
	
	private SecretKeySpec sKeySpec;
	private Cipher cipher;
	private IvParameterSpec iv;
	
	// METODO CONSTRUCTOR
	
	public AES() throws Exception {
		
		// Instanciamos un Generador de llaves en tipo AES
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// Inicializamos el generador especificandole un tamanyo de 128 bytes
		kgen.init(128);
		// Generamos un objeto de cifrado de tipo AES
		cipher = Cipher.getInstance("AES");
		// Instanciamos una llave secreta
		SecretKey llave = kgen.generateKey();
		// Codificamos dicha llave en bytes
		byte[] crudo = llave.getEncoded();
		// Construimos una clave secreta especificando que es de tipo AES
		sKeySpec = new SecretKeySpec(crudo, "AES");
		//Especificamos el parametro iv que se usará
		String sIv = "AES/CBC/PKCS5Padding";
		iv = new IvParameterSpec(sIv.getBytes());
		cipher.getInstance("AES/CBC/PKCS5Padding");
	}
	
	
	
	/**
	 * Emplearemos una funcion que convierte un array de bytes en una cadena
	 * dicha funcion la utilizaremos para desplegar el contenido del
	 * criptosistema
	 * 
	 * @param  array de bytes a convertir en String
	 * @return  El String
	 */
	
	public String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		for(int i=0; i<buf.length; i++) {
			if(((int) buf[i] & 0xff) < 0x10) {
				strbuf.append("0");
			}
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	
	
	/**
	 * Encriptamos el archivo pasado por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return mensaje encriptado en String
	 */
	
	public String encriptarArchivo (String s) throws Exception {
		
		// Inicializamos el sistema de ahora en modo de ENCRIPTACION con la clave del constructor
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
		// Encriptamos el mensaje
		byte[] encriptado = cipher.doFinal(s.getBytes());
		
		return asHex(encriptado);
	}
	
	
	/**
	 * Desencriptamos el archivo pasado por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return mensaje en claro en String
	 */
	
	public String desencriptarArchivo(String s) throws Exception {
		
		// Inicializamos el sistema de ahora en modo de DESENCRIPTACION con la clave del constructor
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		// Obtenemos el array de bytes de lo decriptado
		byte[] desencriptado = cipher.doFinal(s.getBytes());
		
		return (new String(desencriptado));
	}
	
	
	/**
	 * Getter de la clave privada
	 * 
	 * @throws Exception
	 * @return skeySpec
	 */
	
	public String getClavePrivada() {
		byte[] llave = sKeySpec.getEncoded();
		return (asHex(llave));
	}

}