
package communication;

import java.util.List;

public class LogoutResponse extends Response {
    public LogoutResponse(boolean success) {
        super(success, null);
    }
    
    public LogoutResponse(boolean success, List<String> updateData) {
        super(success, updateData);
    }
}
