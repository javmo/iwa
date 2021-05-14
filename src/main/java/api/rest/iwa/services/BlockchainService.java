package api.rest.iwa.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import api.rest.iwa.config.BlockchainProperties;
import api.rest.iwa.models.BalanceDTO;
import api.rest.iwa.models.TransactionDTO;
import api.rest.iwa.models.WalletDTO;



@Service
public class BlockchainService {

	@Autowired
    private final Web3j web3j;
	@Autowired
	private final Admin admin;

    private String coinbaseAddress;
    private final BlockchainProperties config;


    public BlockchainService(Web3j web3j, Admin admin, BlockchainProperties config) {
        this.web3j = web3j;
		this.admin = admin;
		this.config = config;
    }

    public BalanceDTO getBalance(String address) throws IOException, InterruptedException, ExecutionException {
		BigDecimal balance = getBalanceEther(web3j, address);
		System.out.println("balacne eth: " + balance);

		return new BalanceDTO(address, balance);
    }


	public TransactionDTO createToken(String toAddress, BigDecimal amount) throws Exception {

		coinbaseAddress = getCoinbase();
		BigInteger amountWei =  etherToWei(amount);

		TransactionReceipt trx = demoTransfer(coinbaseAddress, toAddress, amountWei);

		TransactionDTO t = new TransactionDTO();
		t.setFromAccount(coinbaseAddress);
		t.setFromPassAccount("TOKEN_CREATION");
		t.setToAcccount(toAddress);
		t.setHash(trx.getBlockHash());
		t.setGasUsed(weiToEther(trx.getCumulativeGasUsed()));
		t.setAmount(amount);
		t.setTime(LocalDateTime.now());

		return t;
    }

	public WalletDTO createAccount(String password) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

		NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();

		return new WalletDTO(newAccountIdentifier.getAccountId(), password);
	}

	public List<WalletDTO> getAllWallets() throws IOException {

		PersonalListAccounts listAccounts = admin.personalListAccounts().send();

		List<String> idsAccount = listAccounts.getAccountIds();

		List<WalletDTO> walletDTOs = new ArrayList<WalletDTO>();

		idsAccount.forEach((idAccount) -> {
			walletDTOs.add( new WalletDTO(idAccount));
		});

		return walletDTOs;
	}

	public TransactionDTO send(TransactionDTO t) throws Exception {

		BigInteger amountWei = etherToWei(
			t.getAmount()
		);
		BigInteger noce = getNonce(web3j,t.getFromAccount());

		EthSendTransaction response = admin.personalSendTransaction(
			new Transaction(
				t.getFromAccount(),
				noce,
				config.gas().getGasPrice(),
				config.gas().getGasLimit(),
				t.getToAcccount(),
				amountWei,
				""
				), t.getFromPassAccount()
		).send();

		t.setHash(response.getTransactionHash());

		TransactionReceipt receipt = waitForReceipt(web3j, t.getHash());
		t.setTime(LocalDateTime.now());
		BigInteger gasUsed = receipt.getCumulativeGasUsed();
		t.setGasUsed(weiToEther(gasUsed));
	
		return t;
	}


    public TransactionReceipt demoTransfer(String fromAddress, String toAddress, BigInteger amountWei)
			throws Exception
	{
		System.out.println("Accounts[1] (to address) " + toAddress + "\n" + 
				"Balance before Tx: " + getBalanceEther(web3j, toAddress) + "\n");

		System.out.println("Transfer " + weiToEther(amountWei) + " Ether to account");

	
		EthGetTransactionCount transactionCount = web3j
				.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST)
				.sendAsync()
				.get();

		BigInteger nonce = transactionCount.getTransactionCount();
		System.out.println("Nonce for sending address (coinbase): " + nonce);

	
		Transaction transaction = Transaction
				.createEtherTransaction(
						fromAddress, 
						nonce, 
						config.gas().getGasPrice(), 
						config.gas().getGasLimit(), 
						toAddress, 
						amountWei);


		EthSendTransaction response = web3j
				.ethSendTransaction(transaction)
				.sendAsync()
				.get();

		String txHash = response.getTransactionHash();		
		System.out.println("Tx hash: " + txHash);


		TransactionReceipt receipt = waitForReceipt(web3j, txHash);
		
		BigInteger gasUsed = receipt.getCumulativeGasUsed();

		System.out.println("Balance after Tx: " + getBalanceEther(web3j, toAddress));
		return receipt;
	}

	public String getCoinbase() throws InterruptedException, ExecutionException {
		return web3j
				.ethCoinbase()
				.sendAsync()
				.get()
				.getResult();
	}


    public static BigDecimal getBalanceEther(Web3j web3j, String address) throws InterruptedException, ExecutionException {
		return weiToEther(getBalanceWei(web3j, address));
	}
	
	/**
	 * Returns the balance (in Wei) of the specified account address. 
	 */
	public static BigInteger getBalanceWei(Web3j web3j, String address) throws InterruptedException, ExecutionException {
		EthGetBalance balance = web3j
				.ethGetBalance(address, DefaultBlockParameterName.LATEST)
				.sendAsync()
				.get();

		return balance.getBalance();
	}
    
    /**
	 * Converts the provided Wei amount (smallest value Unit) to Ethers. 
	 */
	public static BigDecimal weiToEther(BigInteger wei) {
		return Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
	}
	
	public static BigInteger etherToWei(BigDecimal ether) {
		return Convert.toWei(ether, Convert.Unit.ETHER).toBigInteger();
	}

    public static BigInteger getNonce(Web3j web3j, String address) throws InterruptedException, ExecutionException {
		EthGetTransactionCount ethGetTransactionCount = 
				web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();

		return ethGetTransactionCount.getTransactionCount();
	}

    /**
	 * Waits for the receipt for the transaction specified by the provided tx hash.
	 * Makes 40 attempts (waiting 1 sec. inbetween attempts) to get the receipt object.
	 * In the happy case the tx receipt object is returned.
	 * Otherwise, a runtime exception is thrown. 
	 */

    public static TransactionReceipt waitForReceipt(Web3j web3j, String transactionHash) 
			throws Exception 
	{

		int attempts = 40;
		int sleep_millis = 1000;
		
		Optional<TransactionReceipt> receipt = getReceipt(web3j, transactionHash);

		while(attempts-- > 0 && !receipt.isPresent()) {
			Thread.sleep(sleep_millis);
			receipt = getReceipt(web3j, transactionHash);
		}

		if (attempts <= 0) {
			throw new RuntimeException("No Tx receipt received");
		}

		return receipt.get();
	}

	/**
	 * Returns the TransactionRecipt for the specified tx hash as an optional.
	 */
	public static Optional<TransactionReceipt> getReceipt(Web3j web3j, String transactionHash) 
			throws Exception 
	{
		EthGetTransactionReceipt receipt = web3j
				.ethGetTransactionReceipt(transactionHash)
				.sendAsync()
				.get();

		return receipt.getTransactionReceipt();
	}

}
