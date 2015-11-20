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
 * SingleAddStatement prepares the add statement for a single entity.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public class SingleAddStatement extends SimpleStatement {

    public SingleAddStatement(Class entityClass, Object entity) {
        super(entityClass, entity);
    }

    @Override
    public PreparedStatement getStatement() {
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection();
            StringBuilder insert = new StringBuilder("INSERT INTO " + this.tableName + " (");
            StringBuilder values = new StringBuilder(" VALUES (");
            int a = 0;
            for (Map.Entry<Getter, Method> column : getterMethodHashMap.entrySet()) {
                if (a != getterMethodHashMap.size() - 1) {
                    values.append("?,");
                    insert.append(column.getKey().name()).append(",");
                } else {
                    values.append("?)");
                    insert.append(column.getKey().name()).append(")");
                }
                a++;
            }
            a = 1;
            insert.append(values.toString());
            statement = connection.prepareStatement(insert.toString());
            for (Map.Entry<Getter, Method> column : getterMethodHashMap.entrySet()) {
                if (activeGetter != null) setActive(column, entity, (byte) 1);
                Object value = null;
                try {
                    value = column.getValue().invoke(entity);
                    if (!column.getKey().nullable() && value == null) {
                        throw new NullPointerException("Value of " + column.getKey().name() + " can not be null");
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                statement.setObject(a, value);
                a++;
            }
        } catch (SQLException | IOException | PropertyVetoException e) {
            e.printStackTrace();
        }
        return statement;
    }

}
