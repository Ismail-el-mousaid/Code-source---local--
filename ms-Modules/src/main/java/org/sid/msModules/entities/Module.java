package org.sid.msModules.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Module {
    @Id
    private Long id;
    private String name;

}
