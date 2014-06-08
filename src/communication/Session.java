
package communication;

import java.io.Serializable;

public class Session implements Serializable {
    private final String _name;
    private final String _id;
    
    public Session(String name, String id) {
        _name = name;
        _id = id;
    }
    
    public String getName() {
        return _name;
    }
    
    public String getID() {
        return _id;
    }
}
