
package communication;

import database.*;

public class Borrow extends Request {
    private final ItemTag _itemTag;
    private final Duration _duration;
    
    public Borrow(Session session, ItemTag itemTag, Duration duration) {
        super("Borrow", session);
        
        _itemTag = itemTag;
        _duration = duration;
    }
    
    public ItemTag getItemTag() {
        return _itemTag;
    }
    
    public Duration getDuration() {
        return _duration;
    }
}
