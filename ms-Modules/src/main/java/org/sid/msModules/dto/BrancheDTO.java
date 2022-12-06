package org.sid.msModules.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.sid.msModules.entities.Module;

import java.util.Date;

@Data
public class BrancheDTO {
    @ApiModelProperty(notes = "Id du Branche")
    private Long id;
    @ApiModelProperty(notes = "Nom du Branche")
    private String name;
    @ApiModelProperty(notes = "Date création du Branche")
    private Date dateCreation;
    @ApiModelProperty(notes = "Module où appartient la Branche")
    private Module module;

}
