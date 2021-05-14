package api.rest.iwa.models;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Wallets")
public class WalletDTO {

    public WalletDTO () {

    }

    public WalletDTO (String address) {
        super();
        this.address = address;
    }

    public WalletDTO (String address, String password) { 
        super();
        this.address = address;
        this.password = password;
    }

    @Id
    private String _id;

    private String address;

    private String password; 

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAdrress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
            " address='" + getAddress() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
    
}
