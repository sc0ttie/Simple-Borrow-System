
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
        
        if (updateData != null) {
            _updateData = new ArrayList<String>(updateData);
        } else {
            _updateData = new ArrayList<String>();
        }
    }
    
    public boolean succes() {
        return _success;
    }
    
    public boolean isUpdated() {
        return !_updateData.isEmpty();
    }
    
    public List<String> getUpdateData() {
        return _updateData;
    }
    
    public void setUpdateData(List<String> data) {
        for (String s : data) {
            _updateData.add(s);
        }
    }
}
