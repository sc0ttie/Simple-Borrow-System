
package communication;

import database.ItemTag;

public class AddItem extends Request {
    private final ItemTag _itemTag;
    
    public AddItem(Session session, ItemTag tag) {
        super("Add item", session);
        
        _itemTag = tag;
    }
    
    public ItemTag getItemTag() {
        return _itemTag;
    }
}
