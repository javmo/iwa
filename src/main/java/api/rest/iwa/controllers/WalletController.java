package api.rest.iwa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.rest.iwa.models.WalletDTO;
import api.rest.iwa.repositories.IWalletDAO;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, 
    RequestMethod.DELETE})
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private IWalletDAO iWalletDAO;

    @GetMapping("/")
    public List<WalletDTO> getAll()  {
        return iWalletDAO.findAll();
    }

    @GetMapping("/{id}")
    public Optional<WalletDTO> getById(@PathVariable String id)  {
        return iWalletDAO.findById(id);
    }
    
}
