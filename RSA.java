/**
 * 
 * @author Alex
 * Implementacion del Algoritmo RSA
 *
 */

import java.io.*;
import java.security.*;
import javax.crypto.Cipher;

public class RSA {
	
	// VARIABLES DE INSTANCIA
	
	private PublicKey publica;
	private PrivateKey privada;
	private Cipher cipher;
	
	
	// METODO CONSTRUCTOR
	
	public RSA() throws Exception {
		// Instanciamos un generador de claves de tipo RSA
		KeyPairGenerator kgen = KeyPairGenerator.getInstance("RSA");
		// Inicializamos especificando su tamanyo de clave
		kgen.initialize(1024);
		// Obtenemos un repositorio para obtener las claves publica y privada
		KeyPair par = kgen.genKeyPair();
		// Obtenemos las claves
		publica = par.getPublic();
		privada = par.getPrivate();
		
		// Generamos un objeto de cifrado de tipo RSA
		cipher = Cipher.getInstance("RSA");
	}
	
	
	
	/**
	 * Emplearemos una funcion que convierte un array de bytes en una cadena
	 * dicha funcion la utilizaremos para desplegar el contenido del
	 * criptosistema en consola
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
	 * Necesitaremos guardar la clave privada en un fichero de texto
	 * para obtenerla posteriormente y desenciptar la contrasenya
	 * generada con AES
	 * 
	 * @param Clave privada a guardar
	 * @return Un void
	 */
	
	public void guardaClave(PrivateKey clave) {
		FileWriter salida = null;
		try {
			
			// creamos el nombre del fichero
			String nombre = "clave.txt";
			
			// generamos el fichero donde guardamos la clave privada
			salida = new FileWriter(nombre);
			
			// configuramos la clave para guardarla
			byte[] conf = clave.getEncoded();
			String clav = asHex(conf);
			
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
	
	

	/**
	 * Encriptamos el archivo pasado por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return criptosistema en array bytes
	 */
	
	public String encriptarArchivo(String s) throws Exception {
		byte[] devuelve = null;
		
		// modificamos el sistema para que encripte con la clave publica
		cipher.init(Cipher.ENCRYPT_MODE, publica);
		devuelve = cipher.doFinal(s.getBytes());
		
		return asHex(devuelve);
	}
	
	
	
	/**
	 * Decriptamos el archivo pasado por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return mensaje en claro en String
	 */
	
	public String decriptarArchivo(byte[] c) throws Exception {
		byte[] decriptado = null;
		
		// modificamos el sistema para que desencripte con la clave privada
		cipher.init(Cipher.DECRYPT_MODE, privada);
		decriptado = cipher.doFinal(c);
		
		return (new String(decriptado));
	}
	
	

}