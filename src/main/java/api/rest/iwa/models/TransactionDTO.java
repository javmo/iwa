package api.rest.iwa.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Transactions")
public class TransactionDTO {

    @Id
    private String _id;

    private String fromAccount;
    private String fromPassAccount;
    private String toAcccount;
    private BigDecimal amount;

    private String hash;
    private BigDecimal gasUsed;

    private LocalDateTime localDateTime;


    public TransactionDTO() {
    }

    public TransactionDTO(String _id, String fromAccount, String fromPassAccount, String toAcccount, BigDecimal amount, String hash, BigDecimal gasUsed, LocalDateTime localDateTime) {
        this._id = _id;
        this.fromAccount = fromAccount;
        this.fromPassAccount = fromPassAccount;
        this.toAcccount = toAcccount;
        this.amount = amount;
        this.hash = hash;
        this.gasUsed = gasUsed;
        this.localDateTime = localDateTime;
    }


    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFromAccount() {
        return this.fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getFromPassAccount() {
        return this.fromPassAccount;
    }

    public void setFromPassAccount(String fromPassAccount) {
        this.fromPassAccount = fromPassAccount;
    }

    public String getToAcccount() {
        return this.toAcccount;
    }

    public void setToAcccount(String toAcccount) {
        this.toAcccount = toAcccount;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public BigDecimal getGasUsed() {
        return this.gasUsed;
    }

    public void setGasUsed(BigDecimal gasUsed) {
        this.gasUsed = gasUsed;
    }

    public LocalDateTime getTime() {
        return this.localDateTime;
    }

    public void setTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


    @Override
    public String toString() {
        return "{" +
            " _id='" + get_id() + "'" +
            ", fromAccount='" + getFromAccount() + "'" +
            ", fromPassAccount='" + getFromPassAccount() + "'" +
            ", toAcccount='" + getToAcccount() + "'" +
            ", amount='" + getAmount() + "'" +
            ", hash='" + getHash() + "'" +
            ", gasUsed='" + getGasUsed() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
    
}
