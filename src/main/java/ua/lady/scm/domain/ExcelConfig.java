package ua.lady.scm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@ToString
@Getter
@Setter
public class ExcelConfig {
    private int headerRowIndex;
    private int businessIdColumnIndex;
    private Map<String, Integer> columnMapping;
    private BrandStrategy brandStrategy;

    public Integer getColumnIndex(String columnName) {
        return columnMapping.get(columnName);
    }

    public enum BrandStrategy {
        FIRST, LAST
    }


}
