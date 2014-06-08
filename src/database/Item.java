
package database;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private final String _name;
    private final int _permission;
    private final List<Duration> _borrowedTime;
    
    public Item(String name, int permission) {
        _name = name;
        _permission = permission;
        
        _borrowedTime = new ArrayList<>();
    }
    
    public String getName() {
        return _name;
    }
    
    public int getPermissionLevel() {
        return _permission;
    }
    
    public boolean borrow(Duration time) {
        for (Duration duration : _borrowedTime) {
            if (Duration.isOverlapped(duration, time)) {
                return false;
            }
        }
        
        _borrowedTime.add(time);
        
        return true;
    }
    
    public void back(Duration time) {
        _borrowedTime.remove(time);
    }
}
