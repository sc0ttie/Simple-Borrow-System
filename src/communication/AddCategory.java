
package communication;

public class AddCategory extends Request {
    private final String _category;
    
    public AddCategory(Session session, String category) {
        super("Add category", session);
        
        _category = category;
    }
    
    public String getCategory() {
        return _category;
    }
}
