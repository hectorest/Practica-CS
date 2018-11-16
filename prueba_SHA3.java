import javax.crypto.*;
import javax.crypto.spec.*;
import AES.java;

public class prueba_SHA3 {
	
	public static void main(String[] args) throws Exception {
		
		if(args[]!=null && args.length>0) {
			System.out.println("El string original es: " + args[0]);
			String hasheado = SHA3_Ejemplos.getSHA512(args[0]);
			System.out.println("El string hasheado es: " + hasheado);
		}
	}
}
