package org.sid.msModules.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.sid.msModules.dto.EnvironnementDTO;
import org.sid.msModules.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class EnvironnementRestController {
    @Autowired
    private IModuleService moduleService;

    @ApiOperation(value = "Afficher la liste des envs", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/environnements")
    public List<EnvironnementDTO> listEnvironnements(@RequestParam(name = "page", defaultValue = "0")int page,
            @RequestParam(name = "size", defaultValue = "5")int size,
            @RequestParam(name = "keyword", defaultValue = "") String name) {
        log.info("----Get Environnements----");
        return moduleService.getEnvironnements(name, PageRequest.of(page, size));
    }

    @ApiOperation(value = "Get Env By Id", response = EnvironnementDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Env récupéré avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/environnements/{id}")
    public EnvironnementDTO environnement(@PathVariable Long id) {
        log.info("----Get Environnement by id----");
        return moduleService.getEnvironnement(id);
    }


}
