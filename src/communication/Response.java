
package communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Response implements Serializable {
    private boolean _success;
    private List<String> _updateData;
    
    public Response(boolean success) {
        this(success, null);
    }
    
    public Response(boolean success, List<String> updateData) {
        _success = success;
        
        setUpdateData(updateData);
    }
    
    public boolean success() {
        return _success;
    }
    
    public boolean isUpdated() {
        return !_updateData.isEmpty();
    }
    
    public List<String> getUpdateData() {
        return _updateData;
    }
    
    public void setUpdateData(List<String> data) {
        if (data == null) {
            _updateData = new ArrayList<String>();
        } else {
            _updateData = new ArrayList<String>(data);
        }
    }
}
