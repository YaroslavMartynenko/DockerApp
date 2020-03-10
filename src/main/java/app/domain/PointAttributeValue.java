package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "point_attribute_value")
public class PointAttributeValue {

    @Id
    @OneToOne(mappedBy = "pointAttributeValue", cascade = CascadeType.ALL)
    @JoinColumn(name = "point_id", referencedColumnName = "id", nullable = false)
    private Point point;

    @Id
    @OneToOne(mappedBy = "pointAttributeValue", cascade = CascadeType.ALL)
    @JoinColumn(name = "attribute_id", referencedColumnName = "id", nullable = false)
    private Attribute attribute;

    @Column(name = "value")
    private String value;
}
