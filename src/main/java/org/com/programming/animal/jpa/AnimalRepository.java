package org.com.programming.animal.jpa;


import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.ENUMS.AnimalEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {

    @Query("SELECT animais FROM AnimalEntity animais WHERE animais.typeAnimal = :type")
    List<AnimalEntity> findByTypeAnimal(@Param("type") AnimalEnum type);

}
