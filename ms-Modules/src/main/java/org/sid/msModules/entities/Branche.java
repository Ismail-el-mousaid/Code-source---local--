package org.sid.msModules.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Branche {
    @Id
    private Long id;
    private String name;
    private Date dateCreation;

    private Module module;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private Collection<Deploiement> deploiements = new ArrayList<>();
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Tag> tags = new ArrayList<>();

}
