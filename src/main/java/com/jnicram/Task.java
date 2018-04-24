package com.jnicram;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
public class Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);

    private static final String OPERATIONS = "+-/*";

    private static final int MIN_EQUATION_INTEGERS = 2;
    private static final int MAX_EQUATION_INTEGERS = 10;
    private static final int MIN_INTEGER= 1;
    private static final int MAX_INTEGER = 9999999;

    private String equation;

    public void generateRandomEquation() {
        StringBuilder equationBuilder = new StringBuilder();
        int integersCount = getRandomIntegersCount();
        for (int idx = 0 ; idx < integersCount; idx++) {
            equationBuilder.append(getRandomInteger());
            if (idx < integersCount - 1) {
                equationBuilder.append(getRandomOperation());
            }
        }
        equation = equationBuilder.toString();
    }

    public double execute() {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("JavaScript");

        try {
            return Double.valueOf(String.valueOf(engine.eval(equation)));
        } catch (ScriptException e) {
            LOGGER.warn("Generated equation is incorrect!", e);
            return 0D;
        }
    }

    private int getRandomIntegersCount() {
        Random r = new Random();
        return r.ints(MIN_EQUATION_INTEGERS, (MAX_EQUATION_INTEGERS + 1)).limit(1).findFirst().getAsInt();
    }

    private int getRandomInteger() {
        Random r = new Random();
        return r.ints(MIN_INTEGER, (MAX_INTEGER + 1)).limit(1).findFirst().getAsInt();
    }

    private char getRandomOperation() {
        Random random = new Random();
        int index = (int) (random.nextFloat() * OPERATIONS.length());
        return OPERATIONS.charAt(index);
    }
}
