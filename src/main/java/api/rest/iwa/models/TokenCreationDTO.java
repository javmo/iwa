package api.rest.iwa.models;

import java.math.BigDecimal;

public class TokenCreationDTO {

    private String toAddress;

    private BigDecimal amount;


    public String getToAddress() {
        return this.toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
