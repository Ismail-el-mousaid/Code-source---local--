package org.sid.msModules.repository;

import org.sid.msModules.entities.Branche;
import org.sid.msModules.entities.Deploiement;
import org.sid.msModules.entities.Environnement;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
@Order(1)
public interface DeploiementRepository extends MongoRepository<Deploiement, String> {
    List<Deploiement> findByBrancheId(Long idBranche);
    //Get les branches déployées dans un env
    Page<Deploiement> findByEnvironnementId(Long idEnvironnement, Pageable pageable);
    Page<Deploiement> findByBrancheNameContains(String version, Pageable pageable);
    Page<Deploiement> findByBrancheNameContainsAndBrancheModuleNameContainsAndEnvironnementNameContains(String nameBranche, String nameModule, String nameEnv, Pageable pageable);
    Page<Deploiement> findByBrancheNameContainsAndBrancheModuleNameContainsAndEnvironnementNameContainsAndDateDeploiement(String nameBranche, String nameModule, String nameEnv, Date dateDeploiement, Pageable pageable);
    Page<Deploiement> findByBrancheNameContainsAndBrancheModuleNameContainsAndEnvironnementNameContainsOrderByDateDeploiementAsc(String nameBranche, String nameModule, String nameEnv, Pageable pageable);
    Page<Deploiement> findByBrancheNameContainsAndBrancheModuleNameContainsAndEnvironnementNameContainsOrderByDateDeploiementDesc(String nameBranche, String nameModule, String nameEnv, Pageable pageable);
    @Query("select d from Deploiement d where d.dateDeploiement between :dateDebut and :dateFin")
    Page<Deploiement> getByDateDeploiement(Date dateDebut, Date dateFin, Pageable pageable);

}
