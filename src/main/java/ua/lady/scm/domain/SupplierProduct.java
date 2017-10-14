package ua.lady.scm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "supplier")
@Entity
public class SupplierProduct {
    @Id
    private String id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID")
    private Supplier supplier;
    @ElementCollection
    private Set<String> properties;


}
