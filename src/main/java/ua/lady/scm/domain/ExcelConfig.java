package ua.lady.scm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@Setter
@Embeddable
public class ExcelConfig {
    @Column(nullable = true)
    private int headerRowIndex;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Integer> columnMapping = new HashMap<>();
    @Enumerated(EnumType.ORDINAL)
    private BrandStrategy brandStrategy;

    public Integer put(String key, Integer value) {
        return columnMapping.put(key, value);
    }

    public Integer getColumnIndex(String columnName) {
        return columnMapping.get(columnName);
    }

    public enum BrandStrategy {
        FIRST, LAST
    }


}
