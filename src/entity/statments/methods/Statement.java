package entity.statments.methods;


import java.sql.PreparedStatement;

/**
 * Statement is the base for every strategy used for creating prepared statements.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public interface Statement {

    /**
     * Prepares the statement and returns it.
     *
     * @return PreparedStatement
     */
     PreparedStatement getStatement();

}
