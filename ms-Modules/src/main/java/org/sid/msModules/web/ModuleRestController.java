package org.sid.msModules.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.sid.msModules.dto.ModuleDTO;
import org.sid.msModules.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ModuleRestController {
    @Autowired
    private IModuleService moduleService;

    @ApiOperation(value = "Afficher la liste des modules", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/modules")
    public List<ModuleDTO> listModules(@RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "300") int size,
                                       @RequestParam(name = "keyword", defaultValue = "") String name) {
        log.info("----Get Modules----");
        List<ModuleDTO> modules = moduleService.getModules(name, PageRequest.of(page, size));
        return modules;
    }

    @ApiOperation(value = "Get Module By Id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Module récupéré avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/modules/{id}")
    public ModuleDTO module(@PathVariable Long id) {
        log.info("----Get Modules by id----");
        return moduleService.getModule(id);
    }

}
