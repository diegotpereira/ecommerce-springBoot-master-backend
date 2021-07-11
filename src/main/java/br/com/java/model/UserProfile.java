package br.com.java.model;

import javax.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;

    public UserProfile() {
    }

    public UserProfile(long id, String username, String email, String firstname, String lastname) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }    
    
}
