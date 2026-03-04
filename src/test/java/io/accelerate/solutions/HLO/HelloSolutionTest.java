package io.accelerate.solutions.HLO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HelloSolutionTest {
        private HelloSolution hello;

        @BeforeEach
        public void setUp() {
            hello = new HelloSolution();
        }

//        @Test
//        public void say_hello_to_friend() {
//            assertThat(hello.hello("Alice"), equalTo("Hello, Alice"));
//        }
//
//        @Test
//        public void say_hello_to_world() {
//            assertThat(hello.hello(null), equalTo("Hello, World"));
//        }
}
