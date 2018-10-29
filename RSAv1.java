import java.security.*;
import javax.crypto.Cipher;

/**
 * 
 * @author Alex
 * Implementacion del Algoritmo RSA
 *
 */

public class RSA {
	
	/**
	 * Emplearemos una funcion que convierte un array de bytes en una cadena
	 * dicha funcion la utilizaremos para desplegar el contenido del
	 * criptosistema en consola
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
		
		// Instanciamos un generador de claves de tipo RSA
		KeyPairGenerator kgen = KeyPairGenerator.getInstance("RSA");
		
		// Inicializamos especificando su tamanyo de clave
		kgen.initialize(1024);
		
		// Obtenemos un repositorio para obtener las claves publica y privada
		KeyPair par = kgen.genKeyPair();
		
		// Obtenemos las claves
		PublicKey publica = par.getPublic();
		PrivateKey privada = par.getPrivate();
		
		// Generamos un objeto de cifrado de tipo RSA
		Cipher cipher = Cipher.getInstance("RSA");
		
		// Asignamos el mensaje original
		String mensajeOriginal = "Yo creía que de la seguridad se encargaban los informáticos no los multimedias, nuestro trabajo son los usuarios";
		System.out.println("Este es el mensaje: " + mensajeOriginal);
		
	}

}
