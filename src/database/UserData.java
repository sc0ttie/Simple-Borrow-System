
package database;

import java.util.ArrayList;
import java.util.List;
import utility.Utils;

public class UserData {
    private final String _name;
    private String _passwordDigest;
    private String _salt;
    private Permission _permission;
    private List<Item> _borrowedItems;
    
    public UserData(String name, String passwordDigest, Permission permission) {
        _name = name;
        _permission = permission;
        
        setPassword(passwordDigest);
        
        _borrowedItems = new ArrayList<>();
    }
    
    public void setPassword(String passwordDigest) {
        _passwordDigest = passwordDigest;
        
        _salt = Utils.hash(passwordDigest + Utils.randomInt(1000));
    }
    
    public String getName() {
        return _name;
    }
    
    public String getPasswordDigest() {
        return _passwordDigest;
    }
    
    public String getSalt() {
        return _salt;
    }
    
    public Permission getPermission() {
        return _permission;
    }
    
    public void borrow(Item item) {
        _borrowedItems.add(item);
    }
    
    public boolean back(Item item) {
        return _borrowedItems.remove(item);
    }
}
