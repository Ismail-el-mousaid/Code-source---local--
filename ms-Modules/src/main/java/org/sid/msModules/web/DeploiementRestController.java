package org.sid.msModules.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.sid.msModules.entities.Deploiement;
import org.sid.msModules.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@Slf4j
public class DeploiementRestController {
    @Autowired
    private IModuleService moduleService;

    @ApiOperation(value = "Afficher la liste des déploiements by Branche & Module & Env", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/deploiements")
    public Page<Deploiement> listDeploiementsByBrancheAndModuleAndEnv(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "300") int size,
            @RequestParam(value = "nameBranche", defaultValue = "") String nameBranche,
            @RequestParam(value = "nameModule", defaultValue = "") String nameModule,
            @RequestParam(value = "nameEnv", defaultValue = "") String nameEnv) {
        log.info("----Get Deploiements by nameBranche, nameModule or nameEnv----");
        return moduleService.getDeploiementsByBrancheAndModuleAndEnv(nameBranche, nameModule, nameEnv, PageRequest.of(page, size));
    }


    @ApiOperation(value = "Afficher la liste des déploiements By Branche & Module & Env & Date Déploiement", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/deploiementsFindByDate")
    public Page<Deploiement> listDeploiementsByBrancheAndModuleAndEnvAndDateDeploiement(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "nameBranche", defaultValue = "") String nameBranche,
            @RequestParam(value = "nameModule", defaultValue = "") String nameModule,
            @RequestParam(value = "nameEnv", defaultValue = "") String nameEnv,
            @RequestParam(value = "dateDeploiement") String dateDeploiement) throws ParseException {
        log.info("----Find Deploiements by nameBranche, nameModule, nameEnv and Date Deploiement----");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return moduleService.getDeploiementsByBrancheAndModuleAndEnvAndDateDeploiement(nameBranche, nameModule, nameEnv, formatter.parse(dateDeploiement), PageRequest.of(page, size));

    }


    @ApiOperation(value = "Afficher la liste des déploiements triés par date déploiement asc", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/deploiementsTriAsc")
    public Page<Deploiement> listDeploiementsByBrancheAndModuleAndEnvAndTriAsc(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "nameBranche", defaultValue = "") String nameBranche,
            @RequestParam(value = "nameModule", defaultValue = "") String nameModule,
            @RequestParam(value = "nameEnv", defaultValue = "") String nameEnv) {
        log.info("----Tri Deploiements Ascendant----");
        return moduleService.getDeploiementsByBrancheAndModuleAndEnvAndTriAsc(nameBranche, nameModule, nameEnv, PageRequest.of(page, size));
    }

    @ApiOperation(value = "Afficher la liste des déploiements triés par date déploiement desc", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/deploiementsTriDesc")
    public Page<Deploiement> listDeploiementsByBrancheAndModuleAndEnvAndTriDesc(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "nameBranche", defaultValue = "") String nameBranche,
            @RequestParam(value = "nameModule", defaultValue = "") String nameModule,
            @RequestParam(value = "nameEnv", defaultValue = "") String nameEnv) {
        log.info("----Tri Deploiements Descendant----");
        return moduleService.getDeploiementsByBrancheAndModuleAndEnvAndTriDesc(nameBranche, nameModule, nameEnv, PageRequest.of(page, size));
    }


    @ApiOperation(value = "Afficher la liste des déploiements By date déploiement", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/deploiementsDate")
    public Page<Deploiement> getDeploiementsByDateDepl(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "1000") int size) throws ParseException {
        log.info("----Get Deploiements by date deploiement----");
        // Date Now
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateNow = formatter.format(date);

        // DateNow - 10 days
        LocalDate today = LocalDate.now();
        String dateMinus10 = String.valueOf(today.minusDays(9));

        return moduleService.getDeploiementsByDateDeploiement(formatter.parse(String.valueOf(dateMinus10)), formatter.parse(String.valueOf(dateNow)), PageRequest.of(page, size));
    }

}
