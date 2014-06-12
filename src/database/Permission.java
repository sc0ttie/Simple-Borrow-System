
package database;

public enum Permission {
    PUBLIC(0),
    USER(1),
    ADV_USER(2),
    ADMIN(Integer.MAX_VALUE);
    
    private final int _level;
    
    private Permission(int level) {
        _level = level;
    }
    
    public boolean enough(Permission other) {
        return _level >= other._level;
    }
}
