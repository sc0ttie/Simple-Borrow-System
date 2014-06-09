
package database;

import java.io.Serializable;

public class ItemTag implements Serializable {
    private final String _category;
    private final String _name;
    
    public ItemTag(String category, String name) {
        _category = category;
        _name = name;
    }
    
    public String getCategory() {
        return _category;
    }
    
    public String getName() {
        return _name;
    }
    
    @Override
    public String toString() {
        return _category + "/" + _name;
    }
}
