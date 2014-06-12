
package client;

import communication.*;
import database.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    protected boolean _login;
    protected Session _session;
    protected Map<String, List<String>> _items;
    
    protected Socket _socket;
    protected ObjectInputStream _in;
    protected ObjectOutputStream _out;
    
    public User(String hostname, int port) {
        try {
            _socket = new Socket(hostname, port);
            
            _out = new ObjectOutputStream(_socket.getOutputStream());
            _in = new ObjectInputStream(_socket.getInputStream());
            
            _login = false;
            
            _items = (Map) _in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, List<String>> getAllItemList() {
        return _items;
    }
    
    public Session getSession() {
        return _session;
    }
    
    public boolean login(String name, String password) {
        try {
            _out.writeObject(new Login(name, password));
            
            LoginResponse res = (LoginResponse) _in.readObject();
            
            if (res.success()) {
                _session = res.getSession();
                
                _login = true;
            }
            
            update(res.getUpdateData());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return _login;
    }
    
    public boolean query(ItemTag tag, Duration duration) {
        boolean success = false;
        
        try {
            _out.writeObject(new Query(_session, tag, duration));
            
            QueryResponse res = (QueryResponse) _in.readObject();
            
            success = res.success();
            
            update(res.getUpdateData());
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    public boolean borrow(ItemTag tag, Duration duration) {
        boolean success = false;
        
        try {
            _out.writeObject(new Borrow(_session, tag, duration));
            
            BorrowResponse res = (BorrowResponse) _in.readObject();
            
            success = res.success();
            
            update(res.getUpdateData());
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    public void logout() {
        try {
            _out.writeObject(new Logout(_session));
            
            LogoutResponse res = (LogoutResponse) _in.readObject();
            
            _login = false;
            
            update(res.getUpdateData());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() {
        try {
            if (_login) {
                logout();
            }
            
            _out.writeObject(new CloseConnection(_session));
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(List<String> data) {
        for (String s : data) {
            System.out.println(s);
        }
    }
    
    public static void main(String[] args) {
        User scott = new User("localhost", 3000);
        
        for (String category : scott.getAllItemList().keySet()) {
            System.out.println(category);
            for (String item : scott.getAllItemList().get(category)) {
                System.out.println("\t" + item);
            }
        }
        
        boolean result = scott.login("Scott", "s123456");
        
        if (result == true) {
            ItemTag tag = new ItemTag("classroo", "C208");
            Duration duration = new Duration(new Date(), new Date());
            
            System.out.println(scott.query(tag, duration));
            
            scott.borrow(tag, duration);
            
            scott.logout();
        } else {
            System.out.println("invalid username/password");
        }
        
        scott.close();
    }
}
