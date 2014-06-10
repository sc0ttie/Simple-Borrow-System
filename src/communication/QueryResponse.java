
package communication;

import java.util.List;

public class QueryResponse extends Response {
    public QueryResponse(boolean success) {
        super(success);
    }
    
    public QueryResponse(boolean success, List<String> updateData) {
        super(success, updateData);
    }
}
