package org.sid.msModules.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Deploiement {
    @Id
    private String version;
    private Date dateDeploiement;
    private String nameEmploye;
    private String changes;
    private String commit;
    private String releaseNote;
    private Branche branche;
    private Environnement environnement;

}
