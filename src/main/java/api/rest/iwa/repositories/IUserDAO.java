package api.rest.iwa.repositories;

import org.springframework.stereotype.Repository;

import api.rest.iwa.models.UserDTO;

import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface IUserDAO extends MongoRepository<UserDTO, String> {
    
}
