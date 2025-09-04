package org.com.programming.animal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.apache.catalina.User;

@Entity
@Table(name = "tb_animal")
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnimal;

    private String nameAnimal;
    private Integer yearAnimal;
    private String locatedAnimal;
    private String contactAnimal;
    @ManyToOne
    @JoinColumn(name = "idUser")
    private UserEntity userId; // Coluna para suportar a chave estrangeira do usuário.

    public AnimalEntity(){}

    public AnimalEntity(Long idAnimal, String nameAnimal, Integer yearAnimal, String locatedAnimal, String contactAnimal, UserEntity userId) {
        this.idAnimal = idAnimal;
        this.nameAnimal = nameAnimal;
        this.yearAnimal = yearAnimal;
        this.locatedAnimal = locatedAnimal;
        this.contactAnimal = contactAnimal;
        this.userId = userId;
    }

    public Long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNameAnimal() {
        return nameAnimal;
    }

    public void setNameAnimal(String nameAnimal) {
        this.nameAnimal = nameAnimal;
    }

    public Integer getYearAnimal() {
        return yearAnimal;
    }

    public void setYearAnimal(Integer yearAnimal) {
        this.yearAnimal = yearAnimal;
    }

    public String getLocatedAnimal() {
        return locatedAnimal;
    }

    public void setLocatedAnimal(String locatedAnimal) {
        this.locatedAnimal = locatedAnimal;
    }

    public String getContactAnimal() {
        return contactAnimal;
    }

    public void setContactAnimal(String contactAnimal) {
        this.contactAnimal = contactAnimal;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }
}
