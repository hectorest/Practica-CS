
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
		//!!!!Meted vuestra ruta!!!
		String rutaDestino = "C:\\Users\\neora\\Google Drive\\";
		String password;
		
		if(args.length != 2) {
			System.out.println("Introduce todos los parametros.");
			System.exit(-1);
		}
		
		
		// si todos los parametros son correctos
		try {
			
			
			
			// comprobamos la operacion a realizar: subir o descargar de la nube
			//que danyo ha hecho C++
			if(args[1].equals("E")) {
				
				
				
				// inicializamos las variables AES y RSA principales
				aes1 = new AES();
				rsa1 = new RSA();
				
				// obtenemos el archivo a cifrar
				archivo = new File(args[0]);
				
				// lo ciframos con AES obteniendo su nombre y el string del cifrado
				String[] archivoCifrado = aes1.encriptarArchivo(archivo);
				
				// ciframos la clave AES usada con el metodo RSA
				String claveAEScifrada = rsa1.encriptarClaveAES(aes1.getClavePrivada());
				
				//Cambiamos la extension del archivoCifrado a txt
				String aTxt1[] = archivoCifrado[1].split("\\.");
				String nombreArchivo1 = aTxt1[0] + ".txt";
				// creamos el .txt remoto que conformara la copia de seguridad con el nombre correspondiente
				String cabecera = claveAEScifrada + "####" + archivoCifrado[0];
				CopiaRemota.crearCopiaSeguridadRemota(rutaDestino + nombreArchivo1, cabecera);
				
				// creamos otro .txt local que contendra la clave privada rsa
				archivoClaveRSAprivada = CopiaRemota.crearCopiaSeguridadRemota("key" + nombreArchivo1, rsa1.getPrivateKey());
				
				// y lo ciframos con una nueva clave AES pero introducida con el usuario
				System.out.print("Introduce la password: ");
				Scanner entradaEscaner = new Scanner(System.in);
				password = entradaEscaner.nextLine();
				entradaEscaner.close();
				//Aplicamos la funcion hash a la contrasenya
				password = SHA3_Ejemplos.getSHA512(password);
				// ciframos con AES, esta password tendra que ser recordada por el usuario
				aes2 = new AES(password);
				String[] archivoCifrado2 = aes2.encriptarArchivo(archivoClaveRSAprivada);
				
				//Cambiamos la extension del archivoCifrado a txt
				String aTxt2[] = archivoCifrado[1].split("\\.");
				String nombreArchivo2 = aTxt2[0] + ".txt";
				// lo guardamos por ultimo en la nube
				CopiaRemota.crearCopiaSeguridadRemota(rutaDestino + "key" + nombreArchivo2, archivoCifrado2[0]);
				archivoClaveRSAprivada.delete();
				
				
			} else if(args[1].equals("D")) {
				//Abrimos el archivo que contiene el fichero codificado
				archivoClaveRSAprivada = new File(rutaDestino+(String)(args[0]));
				FileReader fr = new FileReader(archivoClaveRSAprivada);
				BufferedReader br = new BufferedReader(fr);
				String archRSApriv = null;
				String linea = null;
				while((linea = br.readLine()) != null)
				{
					if(archRSApriv == null)
					{
						archRSApriv = linea;
					}
					else
					{
						archRSApriv = archRSApriv + linea;
					}
				}
				//Leemos el password para descifrar
				System.out.print("Introduce la password: ");
				Scanner entradaEscaner = new Scanner(System.in);
				password = entradaEscaner.nextLine();
				entradaEscaner.close();
				//Aplicamos la funcion hash a la contrasenya
				password = SHA3_Ejemplos.getSHA512(password);
				aes2 = new AES(password);
				//Desencriptamos el archivo que contiene RSA priv con el password
				//Habria que crear una excepcion o guardar la contrasenya en la aplicacion para comparar
				aes2.desencriptarArchivo(password, archRSApriv);
				br.close();
				fr.close();
			}
			
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

}
