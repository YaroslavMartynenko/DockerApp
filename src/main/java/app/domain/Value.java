package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "value")
public class Value implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "point_id", referencedColumnName = "id", nullable = false)
    private Point point;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attribute_id", referencedColumnName = "id", nullable = false)
    private Attribute attribute;

    @Column(name = "value")
    private String value;

}