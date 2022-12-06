package org.sid.msModules.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    private Long id;
    private String message;
    private String releaseNote;
    private String nameBranche;
    private String nameModule;
    private String dateCreation;

    @DBRef
    private Branche branche;

}
