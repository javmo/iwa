package api.rest.iwa.config;

import java.math.BigInteger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.web3j.tx.gas.StaticGasProvider;

@Configuration
@ConfigurationProperties( prefix = "blockchain.propeties" )
public class BlockchainProperties {
    private BigInteger gasPrice;
    private BigInteger gasLimit;


    public StaticGasProvider gas() {
        return new StaticGasProvider(gasPrice, gasLimit);
    }

    
}
