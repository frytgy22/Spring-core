package org.lebedeva.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private Group group;
}
