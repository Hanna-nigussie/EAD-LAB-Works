package com.question2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LombokTest {
    private String name = "ONLINEBOOKS";

    public static void main(String[] args) {
        LombokTest lombokTest = new LombokTest();
        System.out.println(lombokTest.getName());
    }
}
