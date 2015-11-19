package entity.statments.methods;

import entity.annotations.*;
import javafx.util.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kujtimh on 15-11-07.
 */
public abstract class SimpleStatement implements Statement {
    protected HashMap<Setter, Method> setterMethodHashMap = new HashMap<>();
    protected HashMap<Getter, Method> getterMethodHashMap = new HashMap<>();
    protected Pair<String, Method> idGetter;
    protected Pair<String, Method> idSetter;
    protected Pair<String, Method> activeSetter;
    protected Pair<String, Method> activeGetter;
    protected String tableName;
    protected Class entityClass;
    protected Object entity;

    public SimpleStatement(Class entityClass, Object entity) {
        this.entityClass = entityClass;
        this.entity = entity;
        prepare();
    }

    @Override
    public abstract PreparedStatement getStatement();

    private void prepare() {
        try {
            tableName = ((Table) entityClass.getAnnotation(Table.class)).name();
        } catch (NullPointerException e) {
            throw new NullPointerException("The entity class does not specify a table name add @Table annotation to the class");
        }
        Method[] methods = entityClass.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Getter.class) {
                    Getter getter = (Getter) annotation;
                    getterMethodHashMap.put(getter, method);
                }
                if (annotation.annotationType() == Setter.class) {
                    Setter setter = (Setter) annotation;
                    setterMethodHashMap.put(setter, method);
                }
                if ((annotation.annotationType() == Id.class) || (annotation.annotationType() == Active.class)) {
                    if (method.getAnnotation(Getter.class) != null) {
                        Getter getter = method.getAnnotation(Getter.class);
                        if (annotation.annotationType() == Id.class) {
                            idGetter = new Pair<>(getter.name(), method);
                        } else {
                            activeGetter = new Pair<>(getter.name(), method);
                        }
                    } else if (method.getAnnotation(Setter.class) != null) {
                        Setter getter = method.getAnnotation(Setter.class);
                        if (annotation.annotationType() == Id.class) {
                            idSetter = new Pair<>(getter.name(), method);
                        } else {
                            activeSetter = new Pair<>(getter.name(), method);
                        }
                    }
                }
            }
        }
        if (idGetter == null) {
            throw new NullPointerException("The Primary Key getter method of the entity must be defined add @Id annotation to the getter of the id");
        }
        if (idSetter == null) {
            throw new NullPointerException("The Primary Key setter method of the entity must be defined add @Id annotation to the setter of the id");
        }
    }

    protected void setActive(Map.Entry<Getter, Method> column, Object entity, byte active) {
        if (column.getValue().equals(activeGetter.getValue())) {
            setterMethodHashMap.forEach((key, val) -> {
                if (key.name().equals(column.getKey().name())) {
                    try {
                        val.invoke(entity, active);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    protected Object controlNullPointer(Map.Entry<Getter, Method> column, Object entity) {
        Object value = null;
        try {
            value = column.getValue().invoke(entity);
            if (!column.getKey().nullable() && value == null) {
                throw new NullPointerException("Value of " + column.getKey().name() + " can not be null");
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }
}
