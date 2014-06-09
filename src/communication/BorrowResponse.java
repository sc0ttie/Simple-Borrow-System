
package communication;

import java.util.List;

public class BorrowResponse extends Response {
    public BorrowResponse(boolean success) {
        super(success);
    }
    
    public BorrowResponse(boolean success, List<String> updateData) {
        super(success, updateData);
    }
}
