package com.question2;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LombokTest {

    private String name;
    private String category;
    private int stock;

    public static void main(String[] args) {
        LombokTest book = new LombokTest();
        book.setName("ONLINEBOOKS");
        book.setCategory("Fiction");
        book.setStock(100);
        System.out.println(book);
    }
}
