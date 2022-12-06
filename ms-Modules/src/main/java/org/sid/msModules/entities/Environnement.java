package org.sid.msModules.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Collection;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Environnement {
    @Id
    private Long id;
    private String name;
    private String type;
    @DBRef
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private Collection<Deploiement> deploiements;

}
