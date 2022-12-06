package org.sid.msModules.dto;

import lombok.Data;
import java.util.Date;

@Data
public class DeploiementDTO {
    private String version;
    private Date dateDeploiement;
    private String nameEmploye;
    private String changes;
    private String commit;
    private BrancheDTO branche;
    private EnvironnementDTO environnement;

}
