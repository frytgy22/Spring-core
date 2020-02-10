package org.lebedeva.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.sql.Date;

@Data
@Builder
public class Task {
    private Long id;
    private String shortDescription;
    private String longDescription;
    private Timestamp created;
    private Date start;
    private Date end;
    private Status status;
    private Category category;
}
