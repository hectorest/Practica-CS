import java.io.*;
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
	
	private static String asHex(byte buf[]) {
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
	 * Necesitaremos guardar la clave privada en un fichero de texto
	 * para obtenerla posteriormente y desenciptar la contrasenya
	 * generada con AES
	 * 
	 * @param Clave privada a guardar
	 * @return Un void
	 */
	
	private static void guardaClave(PrivateKey clave) {
		FileWriter salida = null;
		try {
			
			// generamos el fichero donde guardamos la clave privada
			salida = new FileWriter("clave.txt");
			
			// configuramos la clave para guardarla
			byte[] conf = clave.getEncoded();
			String clav = RSA.asHex(conf);
			
			// lo guardamos
			salida.write(clav);
			
		} catch(NullPointerException | IOException e) {
			System.err.println("Excepcion: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(salida != null) salida.close();
			} catch(IOException e) {
				System.err.println("Excepcion: " + e.getMessage());
				e.printStackTrace();
			}
		}
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
		
		// Guardamos la clave privada en un .txt
		RSA.guardaClave(privada);
		
		// Generamos un objeto de cifrado de tipo RSA
		Cipher cipher = Cipher.getInstance("RSA");
		
		// Asignamos el mensaje original
		String mensajeOriginal = "Yo creía que de la seguridad se encargaban los informáticos no los multimedias, nuestro trabajo son los usuarios";
		System.out.println("Este es el mensaje: " + mensajeOriginal);
		
		// Inicializamos el sistema en modo de ENCRIPTACION
		// con la misma clave publica obtenida
		cipher.init(Cipher.ENCRYPT_MODE, publica);
		
		// Encriptamos el mensaje
		byte[] encriptado = cipher.doFinal(mensajeOriginal.getBytes());
		
		// Imprimimos el mensaje encriptado
		System.out.println("Mensaje Encriptado: " + RSA.asHex(encriptado));
		
		// Inicializamos el sistema pero esta vez en modo de ENCRIPTACION
		// con la misma clave publica obtenida
		cipher.init(Cipher.DECRYPT_MODE, privada);
		
		// Obtenemos el mensaje decriptado
		byte[] decriptado = cipher.doFinal(encriptado);
		
		// Imprimimos el mensaje decriptado
		System.out.println("Mensaje Desencriptado: " + new String(decriptado));
	}

}
