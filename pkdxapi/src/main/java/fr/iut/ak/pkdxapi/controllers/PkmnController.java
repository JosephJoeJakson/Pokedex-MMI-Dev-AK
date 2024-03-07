package fr.iut.ak.pkdxapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.iut.ak.pkdxapi.services.PkmnService;

@RestController
public class PkmnController {

     private final PkmnService pkmnService;

    public PkmnController(PkmnService pkmnService) {
        this.pkmnService = pkmnService;
    }

    @GetMapping("/pkmn/types")
    public Map<String, Object> getPkmnTypes() {
        List<String> types = pkmnService.getAllPkmnTypes();
        Map<String, Object> response = new HashMap<>();
        response.put("data", types);
        response.put("count", types.size());
        return response;
    }
    
}