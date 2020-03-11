package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "point")
public class Point implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "longtitude")
    private BigDecimal longtitude;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL) //Cascade.ALL ?
    private List<Value> values;

}
