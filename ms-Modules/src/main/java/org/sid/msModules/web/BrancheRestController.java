package org.sid.msModules.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.sid.msModules.dto.BrancheDTO;
import org.sid.msModules.exceptions.BrancheNotFoundException;
import org.sid.msModules.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/branches")
@Api(value="brancheRestController", description="REST API Branche")
@RestController
@Slf4j
public class BrancheRestController {

    @Autowired
    private IModuleService moduleService;

    @ApiOperation(value = "Afficher la liste des branches", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping(value = "", produces = "application/json")
    public List<BrancheDTO> listBranchesByNameAndModuleAndEnv(@RequestParam(name = "page", defaultValue = "0")int page,
                                                              @RequestParam(name = "size", defaultValue = "5")int size,
                                                              @RequestParam(name = "keywordBranche", defaultValue = "")String nameBranche,
                                                              @RequestParam(name = "keywordModule", defaultValue = "")String nameModule){
        log.info("----Récupérer liste des branches----");
        return moduleService.getBranchesByNameBrancheAndNameModule(nameBranche, nameModule, PageRequest.of(page, size));

    }


    @ApiOperation(value = "Get Branch By Id", response = BrancheDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public BrancheDTO branche(@PathVariable Long id) throws BrancheNotFoundException {
        log.info("----Get Branche by id----");
        return moduleService.getBranche(id);
    }


}
