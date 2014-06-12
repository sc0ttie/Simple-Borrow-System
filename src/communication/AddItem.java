
package communication;

import database.Item;

public class AddItem extends Request {
    private final Item _item;
    
    
    public AddItem(Session session, Item item) {
        super("Add item", session);
        
        _item = item;
    }
    
    public Item getItem() {
        return _item;
    }
}
