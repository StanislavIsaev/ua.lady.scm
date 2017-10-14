package ua.lady.scm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString(exclude = "supplier")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID")
    private Supplier supplier;

    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "sup_prod_id")
    private Map<SupplierProduct, Price> prices;
}

