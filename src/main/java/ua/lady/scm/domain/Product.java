package ua.lady.scm.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by stani on 22-Jun-17.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Product {

    private Integer id;

    private String name;

    private String group;

    private String brand;

    private String gender;

    private String volume;

    private String type;


}
