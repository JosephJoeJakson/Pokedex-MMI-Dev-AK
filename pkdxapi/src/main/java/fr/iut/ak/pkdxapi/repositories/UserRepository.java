package fr.iut.ak.pkdxapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import fr.iut.ak.pkdxapi.models.UserData;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<UserData,String>{
   @Query("{ '_id' : ?0 }")
    Optional<UserData> findByLogin(String login);

}
