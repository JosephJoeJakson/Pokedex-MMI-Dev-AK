package fr.iut.ak.pkdxapi.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.iut.ak.pkdxapi.errors.PokemonAlreadyExistException;
import fr.iut.ak.pkdxapi.models.PkmnData;
import fr.iut.ak.pkdxapi.models.PkmnRegion;
import fr.iut.ak.pkdxapi.models.PkmnType;
import fr.iut.ak.pkdxapi.repositories.PkmnRepository;

// Service pour les pokémons
@Service
public class PkmnService {

    // Récupère le PokemonRepozitory
    @Autowired
    private PkmnRepository pkmnRepository;

    // Permet de lister tous les types de Pokémons
    public List <String> getAllPkmnTypes() {
        return Arrays.stream(PkmnType.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    }

    // Permet de créer un pokémon et renvoie une erreu s'il existe déjà
    public PkmnData createPkmn(PkmnData pkmnData) {

        // Vérifie si le pokémon existe
        if (pkmnRepository.findByName(pkmnData.getPokemonName()).isPresent()) {
            throw new PokemonAlreadyExistException("Pokemon already exists", HttpStatus.CONFLICT);
        }
        return pkmnRepository.save(pkmnData);
    }
    
    // Permet d'ajouter une région à un pokémon via son nom et renvoie une erreur si le pokémon n'est pas trouvé
    public PkmnData addRegionToPokemon(String pokemonId, String regionName, int regionNumber) {
        Optional<PkmnData> pkmnDataOptional = pkmnRepository.findByName(pokemonId);
        
        // Vérifie si le pokémon existe
        if (!pkmnDataOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found");
        }

        PkmnData pkmnData = pkmnDataOptional.get();
        PkmnRegion newRegion = new PkmnRegion(regionName, regionNumber);
        
        if (pkmnData.getPokemonRegions() == null) {
            pkmnData.setPokemonRegions(new ArrayList<>());
        }
        
        pkmnData.getPokemonRegions().add(newRegion);

        return pkmnRepository.save(pkmnData);
    }

    // Permet de rechercher des Pokémons à l'aide de différents paramètres
    public Map<String, Object> searchPokemons(String partialName, String typeOne, String typeTwo, int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<PkmnData> pagePkmns;
        // Si il y a une recherhe par le nom
        if (partialName != null) {
            pagePkmns = pkmnRepository.findByPartialName(partialName, paging);
        }
        // Si il y a une recherche par type 
        else if (typeOne != null || typeTwo != null) {
            pagePkmns = pkmnRepository.findByTypes(typeOne, typeTwo, paging);
        } 
        // Sinon renovoie une liste avec un nombre minimum de Pokémon
        else {
            pagePkmns = pkmnRepository.findAll(paging);
        }

        List<PkmnData> pkmnList = pagePkmns.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("data", pkmnList);
        response.put("count", pagePkmns.getTotalElements());

        return response;
    }

    public Optional<PkmnData> findPokemonById(String id) {
        return pkmnRepository.findById(id);
    }

    public Optional<PkmnData> findPokemonByName(String name) {
        return pkmnRepository.findByName(name);
    }

    public PkmnData getPokemon(String id, String name) {
        if (id != null) {
            return pkmnRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));
        } else if (name != null) {
            return pkmnRepository.findByName(name)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Either 'id' or 'name' parameter must be provided");
        }
    }
}

