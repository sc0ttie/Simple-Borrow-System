
package client;

import communication.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private boolean _login;
    private Session _session;
    private Map<String, List<String>> _items;
    
    private Socket _socket;
    private ObjectInputStream _in;
    private ObjectOutputStream _out;
    
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

    public Session getSession() {
        return _session;
    }
    
    public boolean login(String name, String password) {
        try {
            _out.writeObject(new Login(name, password));
            
            LoginResponse res = (LoginResponse) _in.readObject();
            
            if (res.succes()) {
                _session = res.getSession();
                
                _login = true;
            }
            
            update(res.getUpdateData());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return _login;
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
        
        scott.login("Scott", "s123456");
        
        scott.logout();
        
        scott.close();
    }
}
