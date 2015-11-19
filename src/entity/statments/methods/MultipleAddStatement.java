package entity.statments.methods;

import entity.annotations.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kujtimh on 15-11-06.
 */
public class MultipleAddStatement extends SimpleStatement {
    public MultipleAddStatement(Class entityClass, Object entity) {
        super(entityClass, entity);
    }

    @Override
    public PreparedStatement getStatement() {
        Connection connection;
        PreparedStatement statement = null;
        if (entity == null) throw new NullPointerException("The values to be updated must be set");
        ArrayList entities = (ArrayList) entity;
        int b = 1;
        for (Object entity : entities) {
            if (statement == null) {
                statement = new SingleAddStatement(entityClass, entity).getStatement();
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
        return null;//todo implement the statement creation
    }
}
