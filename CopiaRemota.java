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
	public static File crearCopiaSeguridadRemota(String r, String c) {
		
		FileWriter fw = null;
		BufferedWriter br = null;
		File f = null;
		
		try {
			
			// especificamos su ruta
			f = new File(r);
			
			// si no existia creamos el archivo
			if(!f.exists()) {
				f.createNewFile();
			}
			
			// copiamos la cabecera cifrada AES junto con el archivo cifrado
			fw = new FileWriter(f);
			br = new BufferedWriter(fw);
			br.write(c);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fw.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	
	
	public static String leerCopiaSeguridadRemota(File f) {
		
		FileReader fw = null;
		BufferedReader br = null;
		String devuelve = null;
		
		try {
			
			fw = new FileReader(f);
			br = new BufferedReader(fw);
			devuelve = br.readLine();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fw.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return devuelve;
	}
	

	

	public static void main(String[] args) {
		AES aes1, aes2;
		RSA rsa1;
		String password;
		File archivo = null, archivoClaveRSAprivada = null;
		SHA3 hash = new SHA3();
		String rutaDrive = "C:\\Users\\Alex\\Google Drive\\iSecurityCS\\";
		String rutaUploads = "C:\\Users\\Alex\\Desktop\\workspace\\iSecurity\\Uploads\\";
		String rutaDownloads = "C:\\Users\\Alex\\Desktop\\workspace\\iSecurity\\Downloads\\";
		
		
		if(args.length != 2) {
			System.out.println("ERROR: Introduce todos los parametros...");
			System.exit(-1);
		}
		
		
		// si todos los parametros son correctos
		try {
			
			
			
			// comprobamos la operacion a realizar: subir o descargar de la nube
			if(args[1].equals("E")) {
				
				
				// inicializamos las variables AES y RSA principales
				aes1 = new AES();
				rsa1 = new RSA();
				
				// obtenemos el archivo a cifrar
				archivo = new File(rutaUploads + args[0]);
				
				// lo ciframos con AES obteniendo su nombre y el string del cifrado
				String[] archivoCifrado = aes1.encriptarArchivo(archivo);
				
				// ciframos la clave AES usada con el metodo RSA
				String claveAEScifrada = rsa1.encriptarClaveAES(aes1.getClavePrivada());
				
				// creamos el .txt remoto que conformara la copia de seguridad con el nombre correspondiente
				String cabecera = claveAEScifrada + "####" + archivoCifrado[0];
				CopiaRemota.crearCopiaSeguridadRemota(rutaDrive + archivoCifrado[1] + ".txt", cabecera);
				
				// creamos otro .txt local que contendra la clave privada rsa
				archivoClaveRSAprivada = CopiaRemota.crearCopiaSeguridadRemota("key" + archivoCifrado[1] + ".txt", rsa1.getPrivateKey());
				
				// y lo ciframos con una nueva clave AES pero introducida con el usuario
				System.out.print("Introduce la password: ");
				Scanner entradaEscaner = new Scanner(System.in);
				password = entradaEscaner.nextLine();
				entradaEscaner.close();
				
				// ciframos con AES, esta password tendra que ser recordada por el usuario
				aes2 = new AES(hash.getSHA512(password));
				String[] archivoCifrado2 = aes2.encriptarArchivo(archivoClaveRSAprivada);
				
				// lo guardamos por ultimo en la nube con la password para posterior comprobacion
				CopiaRemota.crearCopiaSeguridadRemota(rutaDrive + archivoCifrado2[1], archivoCifrado2[0]);
				archivoClaveRSAprivada.delete();
				
				System.out.println("Copia remota generada correctamente!!!");
				
				
				
			} else if(args[1].equals("D")) {
				File archivo1 = null, archivo2 = null;
				String nombre = args[0];
				
				
				// le pedimos la password al usuario correspondiente para este archivo
				System.out.print("Introduce la password: ");
				Scanner entradaEscaner = new Scanner(System.in);
				password = entradaEscaner.nextLine();
				entradaEscaner.close();
				
				// obtenemos los archivos que estan en la nube
				archivo1 = new File(rutaDrive + args[0]);
				archivo2 = new File(rutaDrive + "key" + args[0]);
				
				// obtenemos sus contenidos
				String contenido1 = CopiaRemota.leerCopiaSeguridadRemota(archivo1);
				String contenido3 = CopiaRemota.leerCopiaSeguridadRemota(archivo2);
				
				// usamos la password introducida para obtener todas las claves y criptosistemas
				aes2 = new AES(hash.getSHA512(password));
				
				// obtenemos el archivo que contiene la clave RSA privada
				aes2.desencriptarArchivo(contenido3, "key" + nombre);
				archivoClaveRSAprivada = new File(rutaDownloads + "key" + nombre);
				
				// obtenemos la clave rsa privada
				String contenido2 = CopiaRemota.leerCopiaSeguridadRemota(archivoClaveRSAprivada);
				rsa1 = new RSA();
				rsa.setPrivateKey(contenido2);
				
				String[] encriptado = contenido1.split("####");
				
				
			}
			
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

}
