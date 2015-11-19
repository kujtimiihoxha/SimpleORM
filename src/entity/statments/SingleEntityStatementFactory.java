package entity.statments;

import entity.statments.methods.SingleAddStatement;
import entity.statments.methods.SingleDeleteStatement;
import entity.statments.methods.SingleGetStatement;
import entity.statments.methods.SingleUpdateStatement;
import java.sql.PreparedStatement;

/**
 * SingleEntityStatementFactory creates statements for single entity transactions.
 *
 * @author Kujtim Hoxha
 * @version 1.0.0
 */
public class SingleEntityStatementFactory extends EntityStatementFactory {

    @Override
    protected PreparedStatement createStatement() {
        return statement.getStatement();
    }

    public PreparedStatement getEntityStatement(Class entityClass, Object entity){
        this.statement =new SingleGetStatement(entityClass,entity);
        return createStatement();
    }
    public PreparedStatement updateEntityStatement(Class entityClass, Object  entity){
        this.statement =new SingleUpdateStatement(entityClass,entity);
        return createStatement();
    }
    public PreparedStatement deleteEntityStatement(Class entityClass,Object  entity){
        this.statement =new SingleDeleteStatement(entityClass,entity);
        return createStatement();
    }
    public PreparedStatement addEntityStatement(Class entityClass,Object  entity){
        this.statement =new SingleAddStatement(entityClass,entity);
        return createStatement();
    }

}
