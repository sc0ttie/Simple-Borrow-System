
package communication;

import java.io.Serializable;

public abstract class Request implements Serializable {
    private final String _requestName;
    private final Session _session;
    
    public Request(String name, Session session) {
        _requestName = name;
        _session = session;
    }
    
    public Session getSession() {
        return _session;
    }
    
    public String getRequestName() {
        return _requestName;
    }
}
