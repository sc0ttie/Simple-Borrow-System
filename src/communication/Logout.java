
package communication;

public class Logout extends Request {
    public Logout(Session session) {
        super("Logout", session);
    }
}
