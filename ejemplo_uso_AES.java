package pruebas;

public class ejemplo_uso_aes {

	public static void main(String[] args) throws Exception {
		
		if(args!=null && args.length==1) {
			System.out.println("El string original es: " + args[0]);
			
			aes aes1 = new AES();
			
			String encriptado = aes1.encriptarArchivo(args[0]);
			System.out.println("El string encriptado es: " + encriptado);
			byte[] encriptadoBytes = encriptado.getBytes();
			String desencriptado = aes1.desencriptarArchivo(encriptado);
			System.out.println("El string desencriptado es: " + desencriptado);
		}
	}
	
}
