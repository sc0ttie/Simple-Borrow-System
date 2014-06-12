
package communication;

import java.util.List;

public class AddCategoryResponse extends Response {
    public AddCategoryResponse(boolean success) {
        super(success);
    }
    
    public AddCategoryResponse(boolean success, List<String> updateData) {
        super(success, updateData);
    }
}
