package fr.iut.ak.pkdxapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.iut.ak.pkdxapi.models.Pkmn;
import fr.iut.ak.pkdxapi.models.PkmnData;
import fr.iut.ak.pkdxapi.services.PkmnService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class PkmnController {

    private final PkmnService pkmnService;

    public PkmnController(PkmnService pkmnService) {
        this.pkmnService = pkmnService;
    }

    @GetMapping("/pkmn/types")
    public Map < String, Object > getPkmnTypes() {
        List < String > types = pkmnService.getAllPkmnTypes();
        Map < String, Object > response = new HashMap < > ();
        response.put("data", types);
        response.put("count", types.size());
        return response;
    }

    @PostMapping("/pkmn")
    public ResponseEntity <Pkmn> createPkmn(@RequestBody PkmnData pkmnData) {
        PkmnData createdPkmn = pkmnService.createPkmn(pkmnData);
        return ResponseEntity.ok(createdPkmn);
    }
    

    @PostMapping("/pkmn/region")
    public ResponseEntity<PkmnData> addRegionToPokemon(@RequestParam("pokemonName") String pokemonName,
                                                       @RequestParam("regionName") String regionName,
                                                       @RequestParam("regionNumber") int regionNumber) {
        PkmnData updatedPokemon = pkmnService.addRegionToPokemon(pokemonName, regionName, regionNumber);
        return ResponseEntity.ok(updatedPokemon);
    }


    @GetMapping("/pkmn/search")
    public ResponseEntity<Map<String, Object>> searchPokemons(
            @RequestParam(required = false) String partialName,
            @RequestParam(required = false) String typeOne,
            @RequestParam(required = false) String typeTwo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Map<String, Object> response = pkmnService.searchPokemons(partialName, typeOne, typeTwo, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pkmn")
    public ResponseEntity<PkmnData> getPokemon(@RequestParam(required = false) String id, 
                                               @RequestParam(required = false) String name) {
        PkmnData pkmnData = pkmnService.getPokemon(id, name);
        return ResponseEntity.ok(pkmnData);
    }

}