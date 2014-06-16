
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
        
        addItem(new Item(new ItemTag("classroom", "C209"), Permission.ADV_USER));
        addItem(new Item(new ItemTag("classroom", "C208"), Permission.ADV_USER));
        
        addItem(new Item(new ItemTag("textbook", "algorithm"), Permission.USER));
        addItem(new Item(new ItemTag("textbook", "data structure"), Permission.USER));
        addItem(new Item(new ItemTag("textbook", "database"), Permission.USER));
        addItem(new Item(new ItemTag("textbook", "system software"), Permission.USER));
        
        addItem(new Item(new ItemTag("movie", "hulk"), Permission.USER));
        addItem(new Item(new ItemTag("movie", "iron man"), Permission.USER));
        addItem(new Item(new ItemTag("movie", "ted"), Permission.USER));
        
    }
    
    public static ItemDatabase getInstance() {
        if (_database == null) {
            _database = new ItemDatabase();
        }
        
        return _database;
    }
    
    public Item getItem(ItemTag tag) {
        if (_items.containsKey(tag.getCategory())) {        
            for (Item item : _items.get(tag.getCategory())) {
                if (item.getName().compareTo(tag.getName()) == 0) {
                    return item;
                }
            }
        }
        
        return null;
    }
    
    public Map<String, List<String>> getItemList() {
        return _itemList;
    }
    
    public boolean isBorrowed(Item item, Duration duration) {
        return item.isBorrowed(duration);
    }
    
    public boolean addCategroy(String category) {
        if (_items.containsKey(category)) {
            return false;
        } else {
            _items.put(category, new ArrayList<Item>());

            _itemList.put(category, new ArrayList<String>());
            
            return true;
        }
    }
    
    public void addItem(Item item) {
        String category = item.getCategory();
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
        if (item != null && user.getPermission().enough(item.getPermission())) {
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
