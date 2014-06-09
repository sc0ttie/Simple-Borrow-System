
package communication;

import java.util.List;

public class LoginResponse extends Response {
    private Session _session;
    
    public LoginResponse(Session session) {
        this(session, null);
    }
    
    public LoginResponse(Session session, List<String> updateData) {
        super(session != null, updateData);
        
        _session = session;
    }
    
    public Session getSession() {
        return _session;
    }
}
