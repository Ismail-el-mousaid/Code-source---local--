package org.sid.msModules.service;

import org.sid.msModules.dto.BrancheDTO;
import org.sid.msModules.dto.DeploiementDTO;
import org.sid.msModules.dto.EnvironnementDTO;
import org.sid.msModules.dto.ModuleDTO;
import org.sid.msModules.entities.*;
import org.sid.msModules.exceptions.BrancheNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


public interface IModuleService {

    /*----------Branches-----------*/
    BrancheDTO getBranche(Long id) throws BrancheNotFoundException;
    List<BrancheDTO> getBranchesByNameBrancheAndNameModule(String nameBranche, String nameModule, Pageable pageable);

    /*----------Modules-----------*/
    List<ModuleDTO> getModules(String nom, Pageable pageable);
    ModuleDTO getModule(Long id);

    /*----------Environnements-----------*/
    List<EnvironnementDTO> getEnvironnements(String name, Pageable pageable);
    EnvironnementDTO getEnvironnement(Long id);

    /*----------Deploiements-----------*/
    List<DeploiementDTO> getDeploiements(String branche, Pageable pageable);
    List<DeploiementDTO> getDeploiementsByBranche(Long idBranche);
    List<DeploiementDTO> getDeploiementsByEnv(Long idEnvironnement, Pageable pageable);
    Page<Deploiement> getDeploiementsByBrancheAndModuleAndEnv(String nameBranche, String nameModule, String nameEnv, Pageable pageable);
    Page<Deploiement> getDeploiementsByBrancheAndModuleAndEnvAndDateDeploiement(String nameBranche, String nameModule, String nameEnv, Date dateDeploiement, Pageable pageable);
    Page<Deploiement> getDeploiementsByBrancheAndModuleAndEnvAndTriAsc(String nameBranche, String nameModule, String nameEnv, Pageable pageable);
    Page<Deploiement> getDeploiementsByBrancheAndModuleAndEnvAndTriDesc(String nameBranche, String nameModule, String nameEnv, Pageable pageable);
    Page<Deploiement> getDeploiementsByDateDeploiement(Date dateDebut, Date dateFin, Pageable pageable);
    void getInfosFromAPI() throws ParseException;

    /*----------Tag-----------*/
    Page<Tag> getTags(Pageable pageable);
    Tag getTag(Long id);

    /*---------Runner---------*/
    public void run(Runnable task);

    Date getRecentDateDeploiement(List<Date> datesList);
    List<String> getDatesDeploiements();

}
