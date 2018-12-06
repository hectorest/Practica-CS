package pruebas;

import java.io.*;
import java.util.Scanner;

import seguridad.*;

/**
 * La aplicacion recibe el nombre del archivo a descifrar y obtiene el archivo
 * correspondiente. Se le pide al usuario la clave que usada para cifrar dicho archivo,
 * la hashea y desencripta el archivo.
 * @author Alex
 *
 */

public class PruebaAES2 {
	

	public static void main(String[] args) {
		if(args.length == 1) {
			FileReader fichero = null;
			BufferedReader br = null;
			try {
				
				
				String nombre = args[0];
				String ruta = "C:\\Users\\Alex\\Desktop\\Papa\\" + nombre;
				String clave = "";
				SHA3 rellena = new SHA3();
				
				File texto = new File(ruta);
				fichero = new FileReader(texto);
				br = new BufferedReader(fichero);
				String contenido = br.readLine();
				System.out.println("Contenido: " + contenido);
				String[] cifrado = contenido.split("####");
				
				System.out.print("Introduce una password: ");
				Scanner esc = new Scanner(System.in);
				clave = esc.nextLine();
				esc.close();
				
				String sha = rellena.getSHA512(clave);
				AES aes1 = new AES(sha);
				System.out.println("Clave AES hasheada: " + sha);
				aes1.desencriptarArchivo(cifrado[1], cifrado[0]);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
					fichero.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
