package communication;

public class CloseConnection extends Request {
    public CloseConnection(Session session) {
        super("Close connection", session);
    }
}
