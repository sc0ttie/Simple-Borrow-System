
package utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    private static Random _random;
    private static MessageDigest _messageDigest;
    private static final String HEX = "0123456789ABCDEF";
    
    static {
        try {
            _random = new Random();
            _messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String hash(String s) {
        _messageDigest.reset();
        
        _messageDigest.update(s.getBytes());
        
        String hash = "";
        
        for (byte b: _messageDigest.digest()) {
            int v = b & 0xff;
            
            hash += HEX.charAt(v >> 4) + HEX.charAt(v & 0xf);
        }
        
        return hash;
    }
    
    public static int randomInt(int upperbound) {
        return _random.nextInt(upperbound);
    }
}
