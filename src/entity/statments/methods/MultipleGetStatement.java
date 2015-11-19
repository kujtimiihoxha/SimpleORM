package entity.statments.methods;


import entity.DataSource;
import entity.statments.conditions.WhereCondition;
import javafx.util.Pair;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * MultipleGetStatement prepares the get statement for a single entity.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public class MultipleGetStatement extends SimpleStatement {
    public MultipleGetStatement(Class entityClass, Object conditions) {
        super(entityClass, conditions);
    }

    @Override
    public PreparedStatement getStatement() {
        WhereCondition conditions = null;
        if (entity != null) {
            conditions = (WhereCondition) entity;
        }
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection();
            StringBuilder sql = new StringBuilder("Select * from " + tableName);
            if (conditions != null) {
                sql.append(" Where ");
                int inx = 0;
                for (Map.Entry<String, Pair<String, String>> condition : conditions.getConditions().entrySet()) {
                    if (inx == 0) {
                        sql.append(condition.getKey()).append(condition.getValue().getKey()).append("? ");
                    } else {
                        sql.append(" AND ").append(condition.getKey()).append(condition.getValue().getKey()).append("? ");

                    }
                    inx++;
                }
                if (activeGetter != null) sql.append(" AND ").append(activeGetter.getKey()).append("=1");

            } else {
                if (activeGetter != null) sql.append(" WHERE ").append(activeGetter.getKey()).append("=1");
            }
            statement = connection.prepareStatement(sql.toString());
            if (conditions != null) {
                int inx = 1;
                for (Map.Entry<String, Pair<String, String>> condition : conditions.getConditions().entrySet()) {
                    statement.setObject(inx, condition.getValue().getValue());
                    inx++;
                }
                return statement;
            } else {
                return statement;
            }
        } catch (SQLException | IOException | PropertyVetoException e) {
            e.printStackTrace();
        }
        return statement;
    }
}
