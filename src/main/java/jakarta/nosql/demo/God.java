package jakarta.nosql.demo;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

import java.util.List;

@Entity
public class God {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String power;

    public God(Long id, String name, String power) {
        this.id = id;
        this.name = name;
        this.power = power;
    }

    public God() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "God{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", power=" + power +
                '}';
    }
}
