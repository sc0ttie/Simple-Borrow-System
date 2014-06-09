
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BorrowServer extends Observable {
    private final int _port;
    private final int _maximumConnections;
    private final ServerSocket _serverSocket;
    
    public BorrowServer() throws IOException {
        this(3000, 50);
    }
    
    public BorrowServer(int port, int maxConnections) throws IOException {
        _port = port;
        _maximumConnections = maxConnections;
        
        _serverSocket = new ServerSocket(_port, _maximumConnections);
    }
    
    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(_port);
        
        while (true) {
            Socket socket = _serverSocket.accept();
            
            pool.execute(new ConnectionHandler(socket, (Observable) this));
        }
    }
    
    public static void main(String[] args) {
        try {
            new BorrowServer(3000, 15).start();
        } catch (IOException ex) {
            Logger.getLogger(BorrowServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
