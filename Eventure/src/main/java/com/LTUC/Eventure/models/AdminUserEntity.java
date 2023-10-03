package com.LTUC.Eventure.models;

<<<<<<< HEAD
=======
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

>>>>>>> origin/reneh-comment
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
<<<<<<< HEAD
=======
@Data
@NoArgsConstructor
>>>>>>> origin/reneh-comment
public class AdminUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
=======
    @Setter(AccessLevel.NONE)
>>>>>>> origin/reneh-comment
    Long id;
    private String username;
    private String password;

<<<<<<< HEAD
    public AdminUserEntity() {
    }

=======
>>>>>>> origin/reneh-comment
    public AdminUserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
<<<<<<< HEAD

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
=======
>>>>>>> origin/reneh-comment
}
