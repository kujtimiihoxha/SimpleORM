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
import java.util.Map;

/**
 * SingleUpdateStatement prepares the statement for a single entity to be updated.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public class SingleUpdateStatement extends SimpleStatement {
    public SingleUpdateStatement(Class entityClass, Object entity) {
        super(entityClass, entity);
    }

    @Override
    public PreparedStatement getStatement() {
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection();
            StringBuilder update = new StringBuilder("UPDATE " + this.tableName);
            StringBuilder set = new StringBuilder(" SET ");
            StringBuilder where = new StringBuilder(" WHERE " + idGetter.getKey() + "=?");
            for (Map.Entry<Getter, Method> column : getterMethodHashMap.entrySet()) {
                try {
                    boolean activeEnabled = activeGetter != null;
                    if (activeEnabled) activeEnabled = column.getKey().name().equals(activeGetter.getKey());
                    if (activeEnabled) activeEnabled = column.getValue().invoke(entity) != null;
                    if ((column.getValue().invoke(entity) != null || activeEnabled) && !column.getKey().name().equals(idGetter.getKey())) {
                        set.append(column.getKey().name()).append("=?,");
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            if (activeGetter != null) where.append("AND ").append(activeGetter.getKey()).append("=1");
            int a = 1;
            update.append(set.substring(0, set.length() - 1)).append(where);
            statement = connection.prepareStatement(update.toString());
            for (Map.Entry<Getter, Method> column : getterMethodHashMap.entrySet()) {
                Object value = null;
                try {
                    value = column.getValue().invoke(entity);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (value != null && !column.getKey().name().equals(idGetter.getKey())) {
                    statement.setObject(a, value);
                    a++;
                }
            }

            try {
                statement.setObject(a, idGetter.getValue().invoke(entity));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        } catch (IOException | SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
        return statement;//todo implement the statement creation
    }
}
