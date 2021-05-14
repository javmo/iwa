package api.rest.iwa.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import api.rest.iwa.models.TransactionDTO;

@Repository
public interface ITransactionDAO extends MongoRepository<TransactionDTO, String>  {
    
}
