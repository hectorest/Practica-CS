
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Base64;
import java.io.*;
import com.google.common.io.Files;

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
	}
	
	//METODO CONSTRUCTOR AL QUE LE PASAS UNA CLAVE SECRETA
	public AES(String cl) throws Exception{
		// Generamos un objeto de cifrado de tipo AES
		cipher = Cipher.getInstance("AES");
		byte[] crudo = cl.getBytes();
		// Construimos una clave secreta especificando que es de tipo AES
		sKeySpec = new SecretKeySpec(crudo, "AES");
	}
	
	
	
	/**
	 * Emplearemos una funcion que convierte un array de bytes en una cadena
	 * dicha funcion la utilizaremos para desplegar el contenido del
	 * criptosistema
	 * 
	 * @param  array de bytes a convertir en String
	 * @return  El String
	 
	
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

	*/
	
	/**
	 * Encriptamos el archivo pasado por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return mensaje encriptado en String
	 */
	
	public String[] encriptarArchivo (File f) throws Exception {
		
		String[] devolver = new String[2];
			
		devolver[1] = f.getName();
		
		// Inicializamos el sistema de ahora en modo de ENCRIPTACION con la clave del constructor
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
		// Encriptamos el mensaje
		  byte[] bytesArray = new byte[(int) f.length()]; 

		  FileInputStream fis = new FileInputStream(f);
		  fis.read(bytesArray); //read file into bytes[]
		  fis.close();
		byte[] encriptado = cipher.doFinal(bytesArray);
		
		devolver[0] = new String(Base64.getEncoder().encode(encriptado));
		
		return devolver;
	}
	
	
	/**
	 * Desencriptamos el archivo pasado por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return mensaje en claro en String
	 */
	
	public void desencriptarArchivo(String s, String nombre) throws Exception {
		
		// Inicializamos el sistema de ahora en modo de DESENCRIPTACION con la clave del constructor
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		// Obtenemos el array de bytes de lo decriptado
		byte[] desencriptado = cipher.doFinal(Base64.getDecoder().decode(s));
		
		//File f = new File("C:\\Users\\Usuario\\Documents\\Materials Universitat\\Tercer Curs\\CS\\Prácticas\\Workspace-Eclipse\\pruebas\\src\\pruebas");
		//Pasamos de bytes a File con ayuda de la libreria Google Guava
		Files.write(desencriptado, new File("C:\\Users\\Usuario\\Documents\\Materials Universitat\\Tercer Curs\\CS\\Prácticas\\" + nombre));
		//return f;
	}
	
	/**
	 * Encriptamos la clave privada de RSA pasada por parametro
	 * 
	 * @param Clave privada RSA
	 * @throws Exception
	 * @return Clave cifrada
	 */
	
	public String encriptarClavePrivRSA (String s) throws Exception {
		
		// Inicializamos el sistema de ahora en modo de ENCRIPTACION con la clave del constructor
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
		// Encriptamos el mensaje
		byte[] encriptado = cipher.doFinal(s.getBytes());
		
		return new String(Base64.getEncoder().encode(encriptado));
	}
	
	
	/**
	 * Desencriptamos la clave privada de RSA pasada por parametro
	 * 
	 * @param Clave cifrada
	 * @throws Exception
	 * @return Clave original RSA
	 */
	
	public String desencriptarClavePrivRSA(String s) throws Exception {
		
		// Inicializamos el sistema de ahora en modo de DESENCRIPTACION con la clave del constructor
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		// Obtenemos el array de bytes de lo decriptado
		byte[] desencriptado = cipher.doFinal(Base64.getDecoder().decode(s));
		
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
		return Base64.getEncoder().encodeToString(llave);
	}

}