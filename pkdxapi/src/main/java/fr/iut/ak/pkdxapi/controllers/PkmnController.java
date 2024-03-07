package fr.iut.ak.pkdxapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.iut.ak.pkdxapi.models.Pkmn;
import fr.iut.ak.pkdxapi.models.PkmnData;
import fr.iut.ak.pkdxapi.models.PkmnRegion;
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
    public ResponseEntity < Pkmn > createPkmn(@RequestBody PkmnData pkmnData) {
        PkmnData createdPkmn = pkmnService.createPkmn(pkmnData);
        return ResponseEntity.ok(createdPkmn);
    }

    @PostMapping("/pkmn/region")
    public ResponseEntity < PkmnData > addRegionToPkmn(@RequestParam String regionName,@RequestParam int regionNumber, @RequestParam String pokemonId) {
        PkmnRegion pkmnRegion = new PkmnRegion(regionName, regionNumber);
        PkmnData updatedPkmn = pkmnService.addRegionToPkmn(pokemonId, pkmnRegion);
        return ResponseEntity.ok(updatedPkmn);
    }


}