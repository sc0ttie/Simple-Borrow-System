
package server;

import communication.*;
import database.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConnectionHandler implements Runnable, Observer {
    private Observable _server;
    private List<String> _updateDataBuffer;
    
    private Socket _socket;
    private ObjectInputStream _in;
    private ObjectOutputStream _out;
    private ItemDatabase _items;
    private UserDatabase _users;
    
    public ConnectionHandler(Socket socket, Observable server) {
        try {
            _server = server;
            _updateDataBuffer = new ArrayList<String>();
            
            _socket = socket;
            
            _out = new ObjectOutputStream(_socket.getOutputStream());
            _in = new ObjectInputStream(_socket.getInputStream());
            
            _items = ItemDatabase.getInstance();
            _users = UserDatabase.getInstance();
            
            _out.writeObject(_items.getItemList());
            
            _server.addObserver(this);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Request request = (Request) _in.readObject();
                Response response = null;
                boolean success = true;
                
                Session session = request.getSession();
                
                if (session != null && !_users.verify(session)) {
                    success = false;
                    System.out.println("verfication failed.");
                }
                
                switch (request.getRequestName()) {
                    case "Login":
                        Login login = (Login) request;
                        
                        session = _users.login(login.getName(), login.getPasswordDigest());
                        
                        response = new LoginResponse(session);
                        
                        if (session != null) {
                            _server.notifyObservers("User " + session.getName() + " login");
                        }
                        
                        break;
                    case "Logout":
                        success = _users.logout(session);
                        
                        response = new LogoutResponse(success);
                        
                        if (success) {
                            _server.notifyObservers("User " + session.getName() + " logout");
                        }
                        
                        break;
                    case "Borrow":
                        Borrow borrow = (Borrow) request;
                        
                        UserData user = _users.getUser(session);
                        Item item = _items.getItem(borrow.getItemTag());
                        
                        success = success && _items.borrow(user, item, borrow.getDuration());
                        
                        response = new BorrowResponse(success);
                        
                        if (success) {
                            _server.notifyObservers("User " + session.getName() + " borrow " + item);
                        }
                        
                        break;
                    case "Back":
                        break;
                    case "Close connection":
                        _server.deleteObserver(this);
                        
                        _socket.close();
                        
                        break;
                }
                
                if (!_socket.isClosed()) {
                    response.setUpdateData(_updateDataBuffer);
                    
                    _out.writeObject(response);
                    _out.flush();
                    
                    _updateDataBuffer.clear();
                } else {
                    break;
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        _updateDataBuffer.add((String) arg);
    }
}
