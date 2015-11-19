package entity.statments.methods;

import entity.DataSource;
import entity.annotations.Getter;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
/**
 * MultipleDeleteStatement prepares the statement for multiple entity to be deleted.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public class MultipleDeleteStatement extends SimpleStatement {
    public MultipleDeleteStatement(Class entityClass, Object entity) {
        super(entityClass, entity);
    }

    @Override

    public PreparedStatement getStatement() {
        PreparedStatement statement = null;
        if (entity == null) throw new NullPointerException("The values to be updated must be set");
        ArrayList entities = (ArrayList) entity;
        int b = 1;
        if (activeSetter != null) {
            for (Object entity : entities) {
                getterMethodHashMap.entrySet().stream().filter(column -> activeSetter.getKey().equals(column.getKey().name())).forEach(column -> setActive(column, entity, (byte) 0));
                if (statement == null) {
                    statement = new SingleUpdateStatement(entityClass, entity).getStatement();
                } else {
                    for (Map.Entry<Getter, Method> column : getterMethodHashMap.entrySet()) {
                        Object value = null;
                        try {
                            value = column.getValue().invoke(entity);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        if (value != null && !column.getKey().name().equals(idGetter.getKey())) {
                            try {
                                statement.setObject(b, value);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            b++;
                        }
                    }
                    try {
                        try {
                            statement.setObject(b, idGetter.getValue().invoke(entity));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    statement.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Connection connection;
            try {
                connection = DataSource.getInstance().getConnection();
                String sql = "DELETE FROM " + tableName + " WHERE " + idGetter.getKey() + "=?";
                statement = connection.prepareStatement(sql);
                for (Object entity : entities) {
                    statement.setObject(1, idGetter.getValue().invoke(entity));
                    statement.addBatch();
                }
            } catch (SQLException | IOException | PropertyVetoException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return statement;
        }
        return statement;//todo implement the statement creation
    }
}
