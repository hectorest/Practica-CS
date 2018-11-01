import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * 
 * @author Alex
 * Implementacion del algoritmo AES
 *
 */

public class AES {

	KeyGenerator kgen;
	
	public AES() {
		
		// Instanciamos un Generador de llaves en tipo AES
				kgen = KeyGenerator.getInstance("AES");
				// Inicializamos el generador especificandole un tamanyo de 128 bytes
				kgen.init(128);
	}
	/**
	 * Emplearemos una funcion que convierte un array de bytes en una cadena
	 * dicha funcion la utilizaremos para desplegar el contenido de lo
	 * encriptado en consola
	 * 
	 * @param  array de bytes a convertir en String
	 * @return  El String
	 */
	
	public static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		for(int i=0; i<buf.length; i++) {
			if(((int) buf[i] & 0xff) < 0x10) {
				strbuf.append("0");
			}
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}
	
	public static void main(String[] args) throws Exception {
		
		
		// Instanciamos una llave secreta
		SecretKey llave = kgen.generateKey();
		// Codificamos dicha llave en bytes
		byte[] crudo = llave.getEncoded();
		// Construimos una clave secreta especificando que es de tipo AES
		SecretKeySpec skeySpec = new SecretKeySpec(crudo, "AES");
		// Generamos un objeto de cifrado de tipo AES
		Cipher cipher = Cipher.getInstance("AES");
		// Asignamos el mensaje original
		String mensajeOriginal = "Yo creía que de la seguridad se encargaban los informáticos no los multimedias, nuestro trabajo son los usuarios";
		System.out.println("Este es el mensaje: " + mensajeOriginal);
		// Inicializamos el sistema de ahora en modo de ENCRIPTACION
		// con la misma clave construida anteriormente
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		// Encriptamos el mensaje
		byte[] encriptado = cipher.doFinal(mensajeOriginal.getBytes());
		// Imprimimos el mensaje encriptado con asHex()
		System.out.println("Mensaje Encriptado: " + asHex(encriptado));
		// Inicializamos ahora el sistema de ahora pero en modo de DECRIPTACION
		// con la misma clave anteriormente construida
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		// Obtenemos el array de bytes de lo decriptado
		byte[] decriptado = cipher.doFinal(encriptado);
		// Imprimos el mensaje ya decriptado
		System.out.println("Mensaje Desencriptado: " + new String (decriptado));
	}

}
