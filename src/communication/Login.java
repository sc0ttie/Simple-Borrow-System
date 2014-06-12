
package communication;

import utility.Utils;

public class Login extends Request {
    private final String _name;
    private final boolean _isAdmin;
    private final String _passwordDigest;
    
    public Login(String name, String password) {
        this(name, password, false);
    }
    
    public Login(String name, String password, boolean admin) {
        super("Login", null);
        
        _name = name;
        _isAdmin = admin;
        _passwordDigest = Utils.hash(password);
    }
    
    public String getName() {
        return _name;
    }
    
    public String getPasswordDigest() {
        return _passwordDigest;
    }
    
    public boolean isAdminLogin() {
        return _isAdmin;
    }
}
