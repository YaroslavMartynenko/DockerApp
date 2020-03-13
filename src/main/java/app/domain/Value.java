package app.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "value")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "point_id", referencedColumnName = "id", nullable = false)
    private Point point;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attribute_id", referencedColumnName = "id", nullable = false)
    private Attribute attribute;

    @Column(name = "value")
    private String value;

}