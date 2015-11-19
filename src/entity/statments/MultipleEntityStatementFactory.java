package entity.statments;

import entity.statments.methods.*;

import java.sql.PreparedStatement;

public class MultipleEntityStatementFactory extends EntityStatementFactory {
    @Override
    protected PreparedStatement createStatement() {
        return statement.getStatement();
    }

    public PreparedStatement getEntitiesStatement(Class entityClass, Object condition){
        this.statement =new MultipleGetStatement(entityClass,condition);
        return createStatement();
    }
    public PreparedStatement updateEntitiesStatement(Class entityClass, Object  entity){
        this.statement =new MultipleUpdateStatement(entityClass,entity);
        return createStatement();
    }
    public PreparedStatement deleteEntitiesStatement(Class entityClass, Object  entity){
        this.statement =new MultipleDeleteStatement(entityClass,entity);
        return createStatement();
    }
    public PreparedStatement addEntitiesStatement(Class entityClass, Object  entity){
        this.statement =new MultipleAddStatement(entityClass,entity);
        return createStatement();
    }
}
