package fr.iut.ak.pkdxapi.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.iut.ak.pkdxapi.models.PkmnType;

@Service
public class PkmnService {

    public List <String> getAllPkmnTypes() {
        return Arrays.stream(PkmnType.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    }
}