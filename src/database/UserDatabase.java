
package database;

import communication.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utility.Utils;

public class UserDatabase {
    private static UserDatabase _database;
    private final Map<String, UserData> _users;
    private final List<UserData> _activeUsers;
    
    private UserDatabase() {
        _users = new HashMap<>();
        _activeUsers = new ArrayList<>();
        
        _users.put("Scott", new UserData("Scott", Utils.hash("s123456"), Permission.USER));
    }
    
    public static UserDatabase getInstance() {
        if (_database == null) {
            _database = new UserDatabase();
        }
        
        return _database;
    }
    
    public boolean verify(Session session) {
        UserData user = _users.get(session.getName());
        
        return user != null && user.getSalt().compareTo(session.getID()) == 0;
    }
    
    public UserData getUser(Session session) {
        if (verify(session)) {
            return _users.get(session.getName());
        } else {
            return null;
        }
    }
    
    public Session login(String name, String password, boolean admin) {
        UserData user = _users.get(name);
        
        if (user == null || (admin && user.getPermission() != Permission.ADV_USER)) {
            return null;
        }
        
        if (password.compareTo(user.getPasswordDigest()) == 0) {
            if (!_activeUsers.contains(user)) {
                _activeUsers.add(user);
            }
            
            return new Session(user.getName(), user.getSalt());
        } else {
            return null;
        }
    }
    
    public boolean logout(Session session) {
        return _activeUsers.remove(_users.get(session.getName()));
    }
    
}
