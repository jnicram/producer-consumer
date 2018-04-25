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
    private static final int MIN_INTEGER = 0;
    private static final int MAX_INTEGER = 999;

    private String equation;

    public void generateRandomEquation() {
        StringBuilder equationBuilder = new StringBuilder();
        int integersCount = getEquationIntegersCount();
        boolean divideAsLastOperation = false;
        for (int idx = 0 ; idx < integersCount; idx++) {
            equationBuilder.append(getRandomInteger(divideAsLastOperation));
            if (idx < integersCount - 1) {
                final char operation = getRandomOperation();
                divideAsLastOperation = '/' == operation;
                equationBuilder.append(operation);
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

    private int getEquationIntegersCount() {
        return getRandomIntegerFromRange(MIN_EQUATION_INTEGERS, MAX_EQUATION_INTEGERS);
    }

    private int getRandomInteger(boolean divideAsLastOperation) {
        return getRandomIntegerFromRange(divideAsLastOperation ? MIN_INTEGER + 1 : MIN_INTEGER, MAX_INTEGER);
    }

    private char getRandomOperation() {
        int index = getRandomIntegerFromRange(0, OPERATIONS.length() - 1);
        return OPERATIONS.charAt(index);
    }

    private int getRandomIntegerFromRange(int min, int max) {
        Random random = new Random();
        return random.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }
}
