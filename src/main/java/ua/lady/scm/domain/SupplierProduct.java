package ua.lady.scm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"supplier", "product"})
@Entity
public class SupplierProduct {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String businessId;

    @Column
    private String name;

    @Embedded
    private Price price;

    @ElementCollection
    private Set<String> properties;

    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID", nullable = false, updatable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
