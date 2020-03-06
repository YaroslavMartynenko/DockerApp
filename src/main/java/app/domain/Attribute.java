package app.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @ManyToMany
    @JoinTable ()
    private Set <Point> points;

}
