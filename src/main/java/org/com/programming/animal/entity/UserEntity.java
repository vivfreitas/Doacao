package org.com.programming.animal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "tb_user")
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String nameUser;
    @Column(unique = true)
    private String emailUser;
    private String passwordUser;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<AnimalEntity> listAnimal;


    public UserEntity(){}

    public UserEntity(Long idUser, String nameUser, String emailUser, String passwordUser, List<AnimalEntity> listAnimal) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.listAnimal = listAnimal;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public List<AnimalEntity> getListAnimal() {
        return listAnimal;
    }

    public void setListAnimal(List<AnimalEntity> listAnimal) {
        this.listAnimal = listAnimal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.passwordUser;
    }

    @Override
    public String getUsername() {
        return this.emailUser;
    }
}
