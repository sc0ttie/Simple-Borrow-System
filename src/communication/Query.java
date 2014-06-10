
package communication;

import database.*;

public class Query extends Request {
    private final ItemTag _itemTag;
    private final Duration _duration;
    
    public Query(Session session, ItemTag tag, Duration duration) {
        super("Query", session);
        
        _itemTag = tag;
        _duration = duration;
    }
    
    public ItemTag getItemTag() {
        return _itemTag;
    }
    
    public Duration getDuration() {
        return _duration;
    }
}
