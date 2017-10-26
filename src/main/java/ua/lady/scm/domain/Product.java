package ua.lady.scm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by stani on 22-Jun-17.
 */
@Data
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
