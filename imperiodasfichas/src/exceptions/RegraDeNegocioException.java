package exceptions;

import java.sql.SQLException;

public class RegraDeNegocioException extends SQLException {
    public RegraDeNegocioException(Throwable cause) {
        super(cause);
    }
}
