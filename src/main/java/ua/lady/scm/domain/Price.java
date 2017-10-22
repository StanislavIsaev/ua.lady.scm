package ua.lady.scm.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Embeddable
public class Price {
    @Column
    private BigDecimal value;

    public Price(double value) {
        this.value = BigDecimal.valueOf(value);
    }
}
