package org.sid.msModules.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.sid.msModules.entities.Tag;
import org.sid.msModules.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class TagRestController {
    @Autowired
    private IModuleService moduleService;

    @ApiOperation(value = "Afficher la liste des tags", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/tags")
    public Page<Tag> listTags(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "7") int size) {
        log.info("----Get Tags----");
        return moduleService.getTags(PageRequest.of(page, size));
    }


    @ApiOperation(value = "Get Tag By Id", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag récupéré avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à voir la ressource"),
            @ApiResponse(code = 403, message = "L'accès à la ressource que vous tentiez d'atteindre est interdit"),
            @ApiResponse(code = 404, message = "La ressource que vous tentiez d'atteindre est introuvable")
    })
    @GetMapping("/tags/{id}")
    public Tag tag(@PathVariable("id") Long id){
        return moduleService.getTag(id);
    }


}
