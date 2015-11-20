package entity.statments.models;

import entity.annotations.*;

import java.sql.Timestamp;


@Table(name = "simpleTable")
public class SimpleModel {
    private int id;
    private String name;
    private Timestamp dateInserted;
    private Timestamp dateUpdated;
    private byte active;

    @Id
    @Getter(name = "Id")
    public int getId() {
        return id;
    }
    @Id
    @Setter(name = "Id")
    public void setId(int id) {
        this.id = id;
    }

    @Getter(name = "Name",nullable = false)
    public String getName() {
        return name;
    }

    @Setter(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    @Getter(name = "DateInserted",nullable = false)
    public Timestamp getDateInserted() {
        return dateInserted;
    }

    @Setter(name = "DateInserted")
    public void setDateInserted(Timestamp dateInserted) {
        this.dateInserted = dateInserted;
    }

    @Getter(name = "DateUpdated")
    public Timestamp getDateUpdated() {
        return dateUpdated;
    }

    @Setter(name = "DateUpdated")
    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Active
    @Getter(name = "Active")
    public byte getActive() {
        return active;
    }

    @Active
    @Setter(name = "Active")
    public void setActive(byte active) {
        this.active = active;
    }
}
