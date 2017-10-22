package ua.lady.scm.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by stani on 22-Jun-17.
 */
@Getter
@Setter
@ToString
//@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    private Integer id;

    private String name;
    @Column(name = "classification")
    private String group;

    private String brand;

    private String gender;

    private String volume;

    private String type;


}
