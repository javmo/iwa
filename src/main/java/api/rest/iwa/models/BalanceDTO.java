package api.rest.iwa.models;

import java.math.BigDecimal;

public class BalanceDTO {

    private String address;
    private BigDecimal balance;


    public BalanceDTO(String address, BigDecimal balance) {
        super();
        this.address = address;
        this.balance = balance;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "{" +
            " address='" + getAddress() + "'" +
            ", balance='" + getBalance() + "'" +
            "}";
    }

    
}
