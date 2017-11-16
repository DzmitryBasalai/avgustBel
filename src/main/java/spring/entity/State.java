package spring.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "state", schema = "avgustbeldb")
public class State implements Serializable {
    private int id;
    private String state;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
