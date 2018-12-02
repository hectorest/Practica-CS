import javax.crypto.*;
import javax.crypto.spec.*;
import AES.java;

public class ejemploUso {
	
	public static void main(String[] args) throws Exception {
		
		if(args[]!=null && args.length==1) {
			System.out.println("El string original es: " + args[0]);
			String encriptado = AES.encriptarArchivo(args[0]);
			System.out.println("El string encriptado es: " + encriptado);
			String desencriptado = AES.desencriptarArchivo(encriptado);
			System.out.println("El string desencriptado es: " + desencriptado);
		}
	}

}
