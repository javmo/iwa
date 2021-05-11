package api.rest.iwa.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class UserDTO {

    @Id
    private String _id;

    private String username;

    private String password;


    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
