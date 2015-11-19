package entity.statments.conditions;

import javafx.util.Pair;

import java.util.HashMap;

/**
 * WhereCondition Defines the conditions to add to the PreparedStatement.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public class WhereCondition implements Condition {
    @Override
    public Condition addCondition(String key, String operator, String value) {
        conditions.put(key,new Pair<>(operator,value));
        return this;
    }

    @Override
    public HashMap<String, Pair<String, String>> getConditions() {
        return conditions;
    }
}
