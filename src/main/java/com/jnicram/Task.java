package com.jnicram;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Task {

    private String equation;

    public void generateRandomEquation() {
        equation = "sfds";
    }

    public int execute() {
        return 0;
    }
}
