package src;
import java.util.Date;

public class Notification {
    private String message;
    private String type;
    private Date timestamp;

    public Notification(String message, String type) {
        this.message = message;
        this.type = type;
        this.timestamp = new Date();
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + message + " at " + timestamp;
    }
}
