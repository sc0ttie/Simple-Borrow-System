
package database;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private final ItemTag _itemTag;
    private final int _permission;
    private final List<Duration> _borrowedTime;
    
    public Item(ItemTag itemTag, int permission) {
        _itemTag = itemTag;
        _permission = permission;
        
        _borrowedTime = new ArrayList<>();
    }
    
    public String getName() {
        return _itemTag.getName();
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
    
    @Override
    public String toString() {
        return _itemTag.toString();
    }
}