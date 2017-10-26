package ua.lady.scm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String name;
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<SupplierProduct> products;
    @Embedded()
    private ExcelConfig excelConfig;
}
