
package client;

import communication.*;
import database.*;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Administrator extends User {
    public Administrator(String hostname, int port) {
        super(hostname, port);
    }
    
    public boolean addItem(String category, String name) {
        boolean success = false;
        
        try {
            ItemTag tag = new ItemTag(category, name);
            
            _out.writeObject(new AddItem(_session, tag));
            
            AddItemResponse res = (AddItemResponse) _in.readObject();
            
            success = res.success();
            
            update(res.getUpdateData());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    public boolean addCategory(String category) {
        boolean success = false;
        
        try {
            _out.writeObject(new AddCategory(_session, category));
            
            AddCategoryResponse res = (AddCategoryResponse) _in.readObject();
            
            success = res.success();
            
            update(res.getUpdateData());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    public static void main(String[] args) {
        Administrator admin = new Administrator("localhost", 3000);
        
        for (String category : admin.getAllItemList().keySet()) {
            System.out.println(category);
            for (String item : admin.getAllItemList().get(category)) {
                System.out.println("\t" + item);
            }
        }
        
        boolean result = admin.login("Scott", "s123456");
        
        if (result == true) {
            ItemTag tag = new ItemTag("classroom", "C208");
            Duration duration = new Duration(new Date(), new Date());
            
            System.out.println(admin.query(tag, duration));
            
            admin.borrow(tag, duration);
            
            admin.logout();
        } else {
            System.out.println("invalid username/password");
        }
        
        admin.close();
    }
}
