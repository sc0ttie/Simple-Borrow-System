
package server;

public class Observable extends java.util.Observable {
    @Override
    public void notifyObservers() {
        setChanged();
        
        super.notifyObservers();
    }
    
    @Override
    public void notifyObservers(Object obj) {
        setChanged();
        
        super.notifyObservers(obj);
    }
}
