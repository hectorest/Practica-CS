package pruebas;

public class ejemplo_uso_sha3 {

	public static void main(String[] args) throws Exception {
		
		if(args!=null && args.length==1) {
			System.out.println("El string original es: " + args[0]);
			
			SHA3_Ejemplos sha3 = new SHA3_Ejemplos();
			
			String hasheado = sha3.getSHA512(args[0]);
			System.out.println("El string hasheado es: " + hasheado);
			
		}
	}
	
}