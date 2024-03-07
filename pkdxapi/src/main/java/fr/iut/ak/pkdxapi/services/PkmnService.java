package fr.iut.ak.pkdxapi.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.iut.ak.pkdxapi.errors.PokemonAlreadyExistException;
import fr.iut.ak.pkdxapi.models.PkmnData;
import fr.iut.ak.pkdxapi.models.PkmnRegion;
import fr.iut.ak.pkdxapi.models.PkmnType;
import fr.iut.ak.pkdxapi.repositories.PkmnRepository;

@Service
public class PkmnService {

    @Autowired
    private PkmnRepository pkmnRepository;
    
    public List <String> getAllPkmnTypes() {
        return Arrays.stream(PkmnType.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    }
    
    public PkmnData createPkmn(PkmnData pkmnData) {
        if (pkmnRepository.findByName(pkmnData.getPokemonName()) != null) {
          throw new PokemonAlreadyExistException("Pokemon already exists", HttpStatus.CONFLICT);
        }
        return pkmnRepository.save(pkmnData);
    }

    public PkmnData addRegionToPkmn(String pokemonId, PkmnRegion pkmnRegion) {
        Optional<PkmnData> pkmnDataOptional = pkmnRepository.findById(pokemonId);
        if (!pkmnDataOptional.isPresent()) {
            throw new PokemonNotFoundException("Pokemon not found", HttpStatus.NOT_FOUND);
        }

        PkmnData pkmnData = pkmnDataOptional.get();
        // Supposant que PkmnData a une liste de PkmnRegion
        pkmnData.getRegions().add(pkmnRegion);
        return pkmnRepository.save(pkmnData);
    }
}