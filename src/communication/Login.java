
package communication;

import utility.Utils;

public class Login extends Request {
    private final String _name;
    private final String _passwordDigest;
    
    public Login(String name, String password) {
        super("Login", null);
        
        _name = name;
        
        _passwordDigest = Utils.hash(password);
    }
    
    public String getName() {
        return _name;
    }
    
    public String getPasswordDigest() {
        return _passwordDigest;
    }
}
