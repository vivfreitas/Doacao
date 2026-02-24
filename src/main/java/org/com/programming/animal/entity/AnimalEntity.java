package org.com.programming.animal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.com.programming.animal.entity.ENUMS.AnimalEnum;

@Entity
@Table(name = "tb_animal")
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnimal;
    @NotBlank
    private String nameAnimal;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AnimalEnum typeAnimal; // Gato ou cachorro.
    @NotBlank
    private String breedAnimal;
    @NotNull
    private Integer yearAnimal;
    @NotBlank
    private String locatedAnimal;
    @NotBlank
    private String contactAnimal;
    @Column(length = 1024)
    @NotBlank
    private String imgUrl;
    @Column(length = 1000)
    @NotBlank
    private String detailsAnimal;
    @ManyToOne
    @JoinColumn(name = "idUser")
    private UserEntity userId; // Coluna para suportar a chave estrangeira do usuário.

    public AnimalEntity(){}

    public AnimalEntity(
            Long idAnimal, String nameAnimal, AnimalEnum typeAnimal, String breedAnimal, Integer yearAnimal,
            String locatedAnimal, String contactAnimal, String imgUrl, String detailsAnimal, UserEntity userId) {
        this.idAnimal = idAnimal;
        this.nameAnimal = nameAnimal;
        this.typeAnimal = typeAnimal;
        this.breedAnimal = breedAnimal;
        this.yearAnimal = yearAnimal;
        this.locatedAnimal = locatedAnimal;
        this.contactAnimal = contactAnimal;
        this.imgUrl = imgUrl;
        this.detailsAnimal = detailsAnimal;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetailsAnimal() {
        return detailsAnimal;
    }

    public void setDetailsAnimal(String detailsAnimal) {
        this.detailsAnimal = detailsAnimal;
    }

    public AnimalEnum getTypeAnimal() {
        return typeAnimal;
    }

    public void setTypeAnimal(AnimalEnum typeAnimal) {
        this.typeAnimal = typeAnimal;
    }

    public String getBreedAnimal() {
        return breedAnimal;
    }

    public void setBreedAnimal(String breedAnimal) {
        this.breedAnimal = breedAnimal;
    }
}
