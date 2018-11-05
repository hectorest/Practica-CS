import java.math.BigInteger;//Para usar enteros grandes
import java.security.MessageDigest;//Necesario para usar SHA-512
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;


public class SHA3_Ejemplos {

	public static String getSHA512(String input){ //Este mola mas

		String toReturn = null;
		try {
			//Se guarda en md un MessageDigest de tipo sHA-512
		    MessageDigest md = MessageDigest.getInstance("SHA-512");
		    //Resetea el mensaje (no se por que)
		    md.reset();
		    //Especifica el array de bytes que se usará, UTF8 en este caso
		    md.update(input.getBytes("utf8"));
		    
		    toReturn = String.format("%040x", new BigInteger(1, md.digest()));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return toReturn;
	}
	
	public String get_SHA_512_SecurePassword(String passwordToHash, String   salt){ //El de arriba mola mas
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return generatedPassword;
	}
}
