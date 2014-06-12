
package database;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private final ItemTag _itemTag;
    private final Permission _permission;
    private final List<Duration> _borrowedTime;
    
    public Item(ItemTag itemTag, Permission permission) {
        _itemTag = itemTag;
        _permission = permission;
        
        _borrowedTime = new ArrayList<>();
    }
    
    public String getCategory() {
        return _itemTag.getCategory();
    }
    
    public String getName() {
        return _itemTag.getName();
    }
    
    public Permission getPermission() {
        return _permission;
    }
    
    public boolean isBorrowed(Duration time) {
        for (Duration duration : _borrowedTime) {
            if (Duration.isOverlapped(duration, time)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean borrow(Duration time) {
        if (!isBorrowed(time)) {
            _borrowedTime.add(time);
            
            return true;
        } else {
            return false;
        }
    }
    
    public void back(Duration time) {
        _borrowedTime.remove(time);
    }
    
    @Override
    public String toString() {
        return _itemTag.toString();
    }
}
