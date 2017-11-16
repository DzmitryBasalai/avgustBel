package spring.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class User {
    private String username;
    private String password;
    private boolean enabled;
    private Collection<UserRole> userRoles = new HashSet<UserRole>(0);;

    public User(){}
    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String username, String password,
                boolean enabled, Collection<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRoles = userRoles;
    }


    @Id
    @Column(name = "username", unique = true, nullable = false, length = 45)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false, length = 60)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Column(name = "enabled", nullable = false)
    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    @OneToMany( fetch = FetchType.LAZY, mappedBy = "user",orphanRemoval=true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public Collection<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Collection<UserRole> userRoleSet) {
        this.userRoles = userRoleSet;
    }

}
