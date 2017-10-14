package ua.lady.scm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
public class Price {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "price_list_id")
    private PriceList priceList;
    @ManyToOne
    @JoinColumn(name = "sup_prod_id")
    private SupplierProduct product;

}
