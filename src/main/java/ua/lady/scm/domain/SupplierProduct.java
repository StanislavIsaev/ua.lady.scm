package ua.lady.scm.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"supplier", "product"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"BUSINESS_ID", "SUPPLIER_ID"})
})
public class SupplierProduct {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "BUSINESS_ID")
    private String businessId;

    @Column
    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "PRICE"))
    private Price price;

    @Column(name = "classification")
    private String group;

    private String brand;

    private String gender;

    private String volume;

    private String type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "SUPPLIER_ID", nullable = false, updatable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
