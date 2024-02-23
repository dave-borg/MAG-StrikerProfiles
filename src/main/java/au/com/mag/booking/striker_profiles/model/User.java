package au.com.mag.booking.striker_profiles.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Pilot's name is mandatory")
    private String name;

    @NotBlank(message="Pilot's email is mandatory")
    @Email(message="Pilot's email is invalid")
    private String email;
    
    private int arn;

    private String phoneNumber;

    public User() {
    }

    /**
     * 
     * @param _name Pilot's name
     * @param _email Pilot's email
     * @param _arn Pilot ARN
     */
    public User(String _name, String _email, int _arn, String _phoneNumber) {
        this.name = _name;
        this.email = _email;
        this.arn = _arn;
        this.phoneNumber = _phoneNumber;
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

    @Override
    public String toString() {
        return "User [arn=" + arn + ", email=" + email + ", name=" + name + ", phoneNumber=" + phoneNumber + "]";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
