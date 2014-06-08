
package database;

import java.util.Date;

class Duration {
    private final Date _from;
    private final Date _to;
    
    public Duration(Date from, Date to) {
        _from = from;
        _to = to;
    }
    
    public Date from() {
        return _from;
    }
    
    public Date to() {
        return _to;
    }
    
    public static boolean isOverlapped(Duration a, Duration b) {
        long af = a._from.getTime();
        long at = a._to.getTime();
        long bf = b._from.getTime();
        long bt = b._to.getTime();
        
        if ((af <= bf && bf < at) || (af <= bt && bt < at)) {
            return true;
        } else if ((bf <= af && af < bt) || (bf <= at && at < bt)) {
            return true;
        } else {
            return false;
        }
    }
}
