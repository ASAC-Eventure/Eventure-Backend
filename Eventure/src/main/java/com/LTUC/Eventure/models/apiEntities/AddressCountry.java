package com.LTUC.Eventure.models.apiEntities;

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
public class AddressCountry {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    public AddressCountry() {
    }

    public AddressCountry(String name) {
        this.name = name;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
=======
@Data
@NoArgsConstructor
public class AddressCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;

    public AddressCountry(String name) {
        this.name = name;
    }
>>>>>>> origin/reneh-comment
}
