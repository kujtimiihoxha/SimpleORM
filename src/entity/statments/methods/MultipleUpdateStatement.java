package entity.statments.methods;

import entity.annotations.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * MultipleUpdateStatement prepares the statement for multiple entity to be updated.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */

public class MultipleUpdateStatement extends SimpleStatement {
    public MultipleUpdateStatement(Class entityClass, Object entity) {
        super(entityClass, entity);
    }

    @Override
    public PreparedStatement getStatement() {
        PreparedStatement statement = null;
        if (entity == null) throw new NullPointerException("The values to be updated must be set");
        ArrayList entities = (ArrayList) entity;
        int b = 1;
        for (Object entity : entities) {
            if (statement == null) {
                statement = new SingleUpdateStatement(entityClass, entity).getStatement();
            } else {
                for (Map.Entry<Getter, Method> column : getterMethodHashMap.entrySet()) {
                    if (activeGetter != null) setActive(column, entity, (byte) 1);
                    Object value = controlNullPointer(column, entity);
                    try {
                        statement.setObject(b, value);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    b++;
                }

            }
            try {
                statement.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return statement;
    }
}
