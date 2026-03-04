package io.accelerate.solutions.HLO;

import io.accelerate.runner.SolutionNotImplementedException;

public class HelloSolution {
    public String hello(String friendName) {
        if (friendName == null) {
            return "Hello, World!";
        } else {
            return "Hello, " + friendName + "!";
        }
    }
}

