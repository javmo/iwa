package api.rest.iwa.controllers;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;


import api.rest.iwa.models.BalanceDTO;
import api.rest.iwa.models.TokenCreationDTO;
import api.rest.iwa.models.TransactionDTO;
import api.rest.iwa.models.WalletDTO;
import api.rest.iwa.repositories.ITransactionDAO;
import api.rest.iwa.repositories.IWalletDAO;
import api.rest.iwa.services.BlockchainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, 
    RequestMethod.DELETE})
@RequestMapping("/blockchain")
@Api( value = "/blockchain", description = "Operations with the blockchain")
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private ITransactionDAO iTransactionDAO;

    @Autowired
    private IWalletDAO iWalletDAO;

    @GetMapping("/coinbase")
    public String getCoinbase() throws InterruptedException, ExecutionException {
        return blockchainService.getCoinbase();
    }

    @GetMapping("/coinbase/balance")
    public BalanceDTO getBalanceCoinbase() throws IOException, InterruptedException, ExecutionException {
        return blockchainService.getBalance (blockchainService.getCoinbase());
    }

    @PostMapping("/account")
    @ApiOperation(
            value = "Create an account on te blockchain",
            notes = "Need a password"
    )
    public WalletDTO createAccount(@Validated @RequestBody String password) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, IOException {
        WalletDTO walletDTO = blockchainService.createAccount(password);
        return iWalletDAO.save(walletDTO);
    }

    @GetMapping("/account")
    public List<WalletDTO> getAll() throws IOException {
        return blockchainService.getAllWallets();
    }

    @PostMapping("/account/send")
    @ApiOperation(
        value = "Transfer token to other account ",
        notes = "Need amount, fromAccount, fromAccount password and toAccount"
    )
    public TransactionDTO sendTransaction(@Validated @RequestBody TransactionDTO t) throws Exception {
        iTransactionDAO.save(blockchainService.send(t));     
        return blockchainService.send(t);
    }

    @GetMapping("/account/balance{address}")
    @ApiOperation(
        value = "Get the balance of an account ",
        notes = "Need the address"
    )
    public BalanceDTO getBalance(@PathVariable String address) throws IOException, InterruptedException, ExecutionException {
        return blockchainService.getBalance(address);
    }

    @PostMapping("/account/createToken")
    @ApiOperation(
        value = "Create token in an account ",
        notes = "Need amount and toAccount"
    )
    public TransactionDTO createToken(@Validated @RequestBody TokenCreationDTO t ) throws Exception {

        TransactionDTO newTrx = blockchainService.createToken(t.getToAddress(), t.getAmount());

        iTransactionDAO.save(newTrx);

        return newTrx;
    }

    
}
