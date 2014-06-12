
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
    
    @Override
    public boolean login(String name, String password) {
        try {
            _out.writeObject(new Login(name, password, true));
            
            LoginResponse res = (LoginResponse) _in.readObject();
            
            if (res.success()) {
                _session = res.getSession();
                
                super._login = true;
            }
            
            update(res.getUpdateData());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return super._login;
    }
    
    public boolean addItem(String category, String name, Permission permission) {
        boolean success = false;
        
        try {
            Item item = new Item(new ItemTag(category, name), permission);
            
            _out.writeObject(new AddItem(_session, item));
            
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
