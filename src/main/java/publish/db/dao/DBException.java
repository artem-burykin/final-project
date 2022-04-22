package publish.db.dao;

import java.sql.SQLException;

/**
 * Database exception.
 * @author Burykin
 */
public class DBException extends SQLException {

    public DBException(String message, Throwable cause) {
    }

    public DBException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public DBException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public DBException(String reason) {
        super(reason);
    }

    public DBException() {
        super();
    }

    public DBException(Throwable cause) {
        super(cause);
    }

    public DBException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public DBException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }
}

