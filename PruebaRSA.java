
public class PruebaRSA {
	public static void main(String []args) throws Exception
	{
		AES instanciaA = new AES();
		String claveA = instanciaA.getClavePrivada();
		System.out.println("------------Clave privada de AES a encriptar-------------");
		System.out.println(claveA);
		RSA instanciaR = new RSA();
		String encr = instanciaR.encriptarClave(claveA);
		System.out.println("------------Clave privada de AES encriptada-------------");
		System.out.println(encr);
		System.out.println("------------Clave privada de AES desencriptada-------------");
		System.out.println(instanciaR.decriptarClave(encr));
		//Probamos a crear un txt y guardar la clave privada de RSA
		instanciaR.guardaClave();
	}
}
