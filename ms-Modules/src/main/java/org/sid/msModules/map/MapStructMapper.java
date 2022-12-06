package org.sid.msModules.map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.sid.msModules.dto.BrancheDTO;
import org.sid.msModules.dto.DeploiementDTO;
import org.sid.msModules.dto.EnvironnementDTO;
import org.sid.msModules.dto.ModuleDTO;
import org.sid.msModules.entities.Branche;
import org.sid.msModules.entities.Deploiement;
import org.sid.msModules.entities.Environnement;
import org.sid.msModules.entities.Module;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
@Order(1)
public interface MapStructMapper {
    BrancheDTO mapToBrancheDTO(Branche branche);
    ModuleDTO mapToModuleDTO(Module module);
    EnvironnementDTO mapToEnvironnementDTO(Environnement environnement);
    List<BrancheDTO> mapToListBranchesDTO(List<Branche> branches);
    List<ModuleDTO> mapToListModulesDTO(List<Module> modules);
    List<EnvironnementDTO> mapToListEnvironnementsDTO(List<Environnement> environnements);
    List<DeploiementDTO> mapToListDeploiementsDTO(List<Deploiement> deploiements);

}
