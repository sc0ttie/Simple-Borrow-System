
package server;

import communication.*;
import database.ItemDatabase;
import database.UserDatabase;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConnectionHandler implements Runnable {
    private Socket _socket;
    private ObjectInputStream _in;
    private ObjectOutputStream _out;
    private ItemDatabase _items;
    private UserDatabase _users;
    
    public ConnectionHandler(Socket socket) {
        try {
            _socket = socket;
            
            _out = new ObjectOutputStream(_socket.getOutputStream());
            _in = new ObjectInputStream(_socket.getInputStream());
            
            _items = ItemDatabase.getInstance();
            _users = UserDatabase.getInstance();
            
            _out.writeObject(_items.getItemList());
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        do {
            try {
                Request req = (Request) _in.readObject();
                
                Session session = req.getSession();
                
                if (session != null && !_users.verify(session)) {
                    _out.writeBoolean(false);
                }
                
                switch (req.getRequestName()) {
                    case "Login":
                        Login login = (Login) req;
                        
                        session = _users.login(login.getName(), login.getPasswordDigest());
                        
                        if (session != null) {
                            _out.writeBoolean(true);
                            
                            _out.writeObject(session);
                            
                            System.out.println("User " + session.getName() + " login.");
                        } else {
                            _out.writeBoolean(false);
                        }
                        break;
                    case "Logout":
                        _users.logout(session);
                        
                        System.out.println("User " + session.getName() + " logout.");
                        
                        break;
                    case "Borrow":
                        break;
                    case "Back":
                        break;
                    case "Close connection":
                        _socket.close();
                        
                        break;
                }
                
                if (!_socket.isClosed()) {
                    _out.flush();
                } else {
                    break;
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }
    
}
