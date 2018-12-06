package pruebas;

/**
 * Se pide al usuario introducir una password. Se crea una clave AES con dicha clave
 * y se imprime por pantalla. Se obtiene el archivo pasado por parametro y
 * se encripta con la clave, y se imprime por pantalla. Se crea un fichero en la nube
 * con la cabecera y el archivo encriptado.
 * @author Alex
 */

import java.io.*;
import java.util.Scanner;
import seguridad.*;

public class PruebaAES {
	
	
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

	public static void main(String[] args) throws Exception {
		
		if(args!=null && args.length==1) {
			String ruta = "C:\\Users\\Alex\\Desktop\\Papa\\";
			String clave = "";
			SHA3 rellena = new SHA3();
			
			System.out.print("Introduce una password: ");
			Scanner esc = new Scanner(System.in);
			clave = esc.nextLine();
			esc.close();
			
			String sha = rellena.getSHA512(clave);
			AES aes1 = new AES(sha);
			System.out.println("Clave AES introducida: " + aes1.getClavePrivada());
			
			File fichero = new File(args[0]);
			String[] encriptado = aes1.encriptarArchivo(fichero);
			System.out.println("El archivo " + encriptado[1] + " encriptado: " + encriptado[0]);
			
			String texto = encriptado[1] + ".txt";
			String contenido = encriptado[1] + "####" + encriptado[0];
			PruebaAES.crearCopiaSeguridadRemota(ruta + texto, contenido);
			
			System.out.println("Clave AES hasheada: " + sha);
		}
	}
	
}
