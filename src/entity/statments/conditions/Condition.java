package entity.statments.conditions;

import javafx.util.Pair;

import java.util.HashMap;

/**
 * Condition base for all condition classes;
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public interface Condition {
    HashMap<String,Pair<String,String>> conditions=new HashMap<>();
    Condition addCondition(String key, String operator, String value);
    HashMap<String,Pair<String,String>> getConditions();
}
