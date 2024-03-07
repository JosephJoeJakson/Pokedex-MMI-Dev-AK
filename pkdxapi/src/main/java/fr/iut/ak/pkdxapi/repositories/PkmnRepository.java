package fr.iut.ak.pkdxapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import fr.iut.ak.pkdxapi.models.PkmnData;
import java.util.Optional;



@Repository
public interface PkmnRepository extends MongoRepository<PkmnData,String>{
   @Query("{ 'name' : ?0 }")
   Optional<PkmnData> findByName(String name);

}