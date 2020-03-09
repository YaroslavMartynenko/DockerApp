package app.domain;

import lombok.*;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Entity
@Table(name = "point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "coordinates")
    private String coordinates;

    @OneToOne(mappedBy = "point", cascade = CascadeType.PERSIST)// CascadeType ?
    private PointAttributeValue pointAttributeValue;

//    @ToString.Exclude
//    @ManyToMany (fetch = FetchType.EAGER)
//    @JoinTable(name = "point_attribute",
//            joinColumns = @JoinColumn(name = "point_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "attribute_id", referencedColumnName = "id"))
//    Set<Attribute> attributes;

}
