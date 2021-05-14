package api.rest.iwa.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.web3j.crypto.Wallet;

import api.rest.iwa.models.WalletDTO;

@Repository
public interface IWalletDAO extends MongoRepository<WalletDTO, String> {
    
}
