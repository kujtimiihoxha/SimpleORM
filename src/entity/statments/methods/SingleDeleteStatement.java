package entity.statments.methods;

import entity.DataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * SingleDeleteStatement prepares the statement for a single entity to be deleted.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public class SingleDeleteStatement extends SimpleStatement {
    public SingleDeleteStatement(Class entityClass, Object entity) {
        super(entityClass, entity);
    }

    @Override
    public PreparedStatement getStatement() {
        if (activeSetter != null) {
            getterMethodHashMap.entrySet().stream().filter(column -> activeSetter.getKey().equals(column.getKey().name())).forEach(column -> setActive(column, entity, (byte) 0));
            return new SingleUpdateStatement(entityClass, entity).getStatement();
        } else {
            Connection connection;
            PreparedStatement statement = null;
            try {
                connection = DataSource.getInstance().getConnection();
                String sql = "DELETE FROM " + tableName + " WHERE " + idGetter.getKey() + "=?";
                statement = connection.prepareStatement(sql);
                statement.setObject(1, idGetter.getValue().invoke(entity));
            } catch (SQLException | IOException | PropertyVetoException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return statement;
        }
    }
}
