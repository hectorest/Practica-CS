package seguridad;

import java.io.*;
import java.util.*;

/**
 * Clase que permite subir subir y descargar archivos
 * de forma segura
 * 
 * @params [Archivo] + [E/D](Encriptar/Decriptar)
 * @author Alex
 *
 */

public class CopiaRemota {
	
	
	// metodo que crea un archivo de texto y escribe en el, especificando su nombre o su ruta
	public static File crearCopiaSeguridadRemota(String r, String c) throws Exception {
		
		// especificamos su ruta
		File f = new File(r);
		
		// si no existia creamos el archivo
		if(!f.exists()) {
			f.createNewFile();
		}
		
		// copiamos la cabecera cifrada AES junto con el archivo cifrado
		FileWriter fw = new FileWriter(f);
		BufferedWriter br = new BufferedWriter(fw);
		br.write(c);
		br.close();
		fw.close();
		
		return f;
	}
	

	

	public static void main(String[] args) {
		AES aes1, aes2;
		RSA rsa1;
		File archivo = null, archivoClaveRSAprivada = null;
		String rutaDestino = "C:\\Users\\Alex\\Google Drive\\iSecurityCS\\";
		String password;
		
		if(args.length != 2) {
			System.out.println("Introduce todos los parametros.");
			System.exit(-1);
		}
		
		
		// si todos los parametros son correctos
		try {
			
			
			
			// comprobamos la operacion a realizar: subir o descargar de la nube
			if(args[1] == "E") {
				
				
				
				// inicializamos las variables AES y RSA principales
				aes1 = new AES();
				rsa1 = new RSA();
				
				// obtenemos el archivo a cifrar
				archivo = new File(args[0]);
				
				// lo ciframos con AES obteniendo su nombre y el string del cifrado
				String[] archivoCifrado = aes1.encriptarArchivo(archivo);
				
				// ciframos la clave AES usada con el metodo RSA
				String claveAEScifrada = rsa1.encriptarClaveAES(aes1.getClavePrivada());
				
				// creamos el .txt remoto que conformara la copia de seguridad con el nombre correspondiente
				String cabecera = claveAEScifrada + "####" + archivoCifrado[0];
				CopiaRemota.crearCopiaSeguridadRemota(rutaDestino + archivoCifrado[1], cabecera);
				
				// creamos otro .txt local que contendra la clave privada rsa
				archivoClaveRSAprivada = CopiaRemota.crearCopiaSeguridadRemota("key" + archivoCifrado[1], rsa1.getPrivateKey());
				
				// y lo ciframos con una nueva clave AES pero introducida con el usuario
				System.out.print("Introduce la password: ");
				Scanner entradaEscaner = new Scanner(System.in);
				password = entradaEscaner.nextLine();
				entradaEscaner.close();
				
				// ciframos con AES, esta password tendra que ser recordada por el usuario
				aes2 = new AES(password);
				String[] archivoCifrado2 = aes2.encriptarArchivo(archivoClaveRSAprivada);
				
				// lo guardamos por ultimo en la nube
				CopiaRemota.crearCopiaSeguridadRemota(rutaDestino + "key" + archivoCifrado2[1], archivoCifrado2[0]);
				archivoClaveRSAprivada.delete();
				
				
			} else if(args[1] == "D") {
				
			}
			
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

}
