package api.rest.iwa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import api.rest.iwa.models.TransactionDTO;
import api.rest.iwa.repositories.ITransactionDAO;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, 
    RequestMethod.DELETE})
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private ITransactionDAO iTransactionDAO;

    @GetMapping("/")
    public List<TransactionDTO> getAll()  {
        return iTransactionDAO.findAll();
    }

    @GetMapping("/{id}")
    public Optional<TransactionDTO> getById(@PathVariable String id)  {
        return iTransactionDAO.findById(id);
    }

    
    
}
