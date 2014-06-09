
package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDatabase {
    private static ItemDatabase _database;
    private final Map<String, List<Item>> _items;
    private final Map<String, List<String>> _itemList;
    
    private ItemDatabase() {
        _items = new HashMap<>();
        _itemList = new HashMap<>();
        
        addItem("classroom", new Item(new ItemTag("classroon", "C209"), 4));
        addItem("classroom", new Item(new ItemTag("classroon", "C208"), 4));
    }
    
    public static ItemDatabase getInstance() {
        if (_database == null) {
            _database = new ItemDatabase();
        }
        
        return _database;
    }
    
    public Item getItem(ItemTag tag) {
        for (Item item : _items.get(tag.getCategory())) {
            if (item.getName().compareTo(tag.getName()) == 0) {
                return item;
            }
        }
        
        return null;
    }
    
    public Map<String, List<String>> getItemList() {
        return _itemList;
    }
    
    public void addCategroy(String category) {
        _items.put(category, new ArrayList<Item>());
        
        _itemList.put(category, new ArrayList<String>());
    }
    
    public void addItem(String category, Item item) {
        List<Item> items = _items.get(category);
        
        if (items == null) {
            items = new ArrayList<Item>();
            
            _items.put(category, items);
            _itemList.put(category, new ArrayList<String>());
        }
        
        items.add(item);
        _itemList.get(category).add(item.getName());
    }
    
    public boolean borrow(UserData user, Item item, Duration duration) {
        if (user.getPermissionLevel() >= item.getPermissionLevel()) {
            if (item.borrow(duration) == true) {
                user.borrow(item);
                
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public void back(UserData user, Item item, Duration duration) {
        if (user.back(item)) {
            item.back(duration);
        }
    }
}
