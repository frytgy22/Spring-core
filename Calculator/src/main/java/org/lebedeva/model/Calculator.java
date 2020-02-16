package org.lebedeva.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
@NoArgsConstructor
@Scope("prototype")
public class Calculator {
    private double firstArg;
    private double secondArg;
    private String operand;

    public Optional<Double> getResult() {
        return ("+").equals(this.operand) ? Optional.of(this.firstArg + this.secondArg) :
                ("-").equals(this.operand) ? Optional.of(this.firstArg - this.secondArg) :
                 ("*").equals(this.operand) ? Optional.of(this.firstArg * this.secondArg) :
                  ("/").equals(this.operand) ? Optional.of(this.firstArg / this.secondArg) : Optional.empty();
    }
}
