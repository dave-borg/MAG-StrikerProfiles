package au.com.mag.booking.striker_profiles.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private int arn;
    private String pwdHash;

    public User(String name2, String email2, int arn2, String pwdHash2) {
        //TODO Auto-generated constructor stub
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getArn() {
        return arn;
    }
    public void setArn(int arn) {
        this.arn = arn;
    }
    public String getPwdHash() {
        return pwdHash;
    }
    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }

}