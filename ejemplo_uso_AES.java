package pruebas;
import java.io.*;


public class ejemplo_uso_aes {

	public static void main(String[] args) throws Exception {
		
		if(args!=null && args.length==1) {
			System.out.println("El string original es: " + args[0]);
			
			aes aes1 = new aes();
			
			File fichero = new File(args[0]);
			
			
			
			String[] encriptado = aes1.encriptarArchivo(fichero);
			System.out.println("El string encriptado es: " + encriptado[0]);
			aes1.desencriptarArchivo(encriptado[0], encriptado[1]);
			System.out.println("El string desencriptado es: ");
		}
	}
	
}
