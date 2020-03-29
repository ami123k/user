package user.sec.user.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"),@UniqueConstraint(columnNames = "email")})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@NotBlank
@Size(max = 20)
    private String username;
@NotBlank
@Size(max = 50)
@Email
private  String email ;
@NotBlank
@Size(max = 200)
private String password;

@Transient
private String passwordConfirm;



    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="id_entreprise")
    private entreprise entrepriseuser;




    @ManyToMany(fetch =FetchType.LAZY)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn (name = "role_id"))
    private Set<Role> role=new HashSet<>();

    public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(max = 200) String password, String passwordConfirm, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.role = roles;
    }

    public User( String username,String email,String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email,  String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = roles;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return role;
    }

    public entreprise getEntrepriseuser() {
        return entrepriseuser;
    }

    public void setEntrepriseuser(entreprise entrepriseuser) {
        this.entrepriseuser = entrepriseuser;
    }

    public void setRoles(Set<Role> roles) {
        this.role = roles;
    }

    private boolean valider;

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public boolean isValider() {
        return valider;
    }

    public void setValider(boolean valider) {
        this.valider = valider;
    }
}

