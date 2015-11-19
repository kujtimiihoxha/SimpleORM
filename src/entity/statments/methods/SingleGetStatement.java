package entity.statments.methods;

import entity.DataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * SingleGetStatement prepares the get statement for a single entity.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public class SingleGetStatement extends SimpleStatement {
    public SingleGetStatement(Class entityClass, Object entity) {
        super(entityClass, entity);
    }

    @Override
    public PreparedStatement getStatement() {
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection();
            StringBuilder sql = new StringBuilder("Select * from " + tableName + " where " + idGetter.getKey() + "=?");
            if (activeGetter != null) sql.append(" AND ").append(activeGetter.getKey()).append("=1");
            statement = connection.prepareStatement(sql.toString());
            statement.setObject(1, entity);
        } catch (SQLException | IOException | PropertyVetoException e) {
            e.printStackTrace();
        }
        return statement;
    }
}
