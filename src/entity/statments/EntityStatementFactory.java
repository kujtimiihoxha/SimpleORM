package entity.statments;

import entity.statments.methods.Statement;

import java.sql.PreparedStatement;

/**
 * EntityStatementFactory is the abstract base of the statement factories.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public abstract class EntityStatementFactory {
    protected Statement statement;

    /**
     * Calls the get statement method from the Statement class.
     * @return              The prepared statement
     */
    protected abstract PreparedStatement createStatement();
}

