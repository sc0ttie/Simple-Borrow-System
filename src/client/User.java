
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
    private String _name;
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
            
//            new Thread() {
//                @Override
//                public void run() {
//                    while (true) {
//                        try {
//                            update();
//                            
//                            Thread.sleep(100);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//            }.start();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Session getSession() {
        return _session;
    }
    
    public synchronized boolean login(String name, String password) {
        try {
            _out.writeObject(new Login(name, password));
            
            if (_in.readBoolean() == true) {
                _session = (Session) _in.readObject();
                
                _login = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return _login;
    }
    
    public synchronized void logout() {
        try {
            _out.writeObject(new Logout(_session));
            
            _login = false;
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void close() {
        try {
            if (_login) {
                logout();
            }
            
            _out.writeObject(new CloseConnection(_session));
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public synchronized void update() {
//        try {
//            if (_in.available() > 0) {
//                String msg = (String) _in.readUTF();
//                
//                System.out.println(msg);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static void main(String[] args) {
        User scott = new User("localhost", 3000);
        
        boolean result = scott.login("Scott", "s123456");
        
        System.out.println(result);
        
        scott.logout();
        
        scott.close();
    }
}
