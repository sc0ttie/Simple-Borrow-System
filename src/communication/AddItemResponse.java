
package communication;

import java.util.List;

public class AddItemResponse extends Response {
    public AddItemResponse(boolean success) {
        super(success);
    }
    
    public AddItemResponse(boolean success, List<String> updateData) {
        super(success, updateData);
    }
}
