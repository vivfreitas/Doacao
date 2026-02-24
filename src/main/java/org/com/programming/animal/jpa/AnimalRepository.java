package org.com.programming.animal.jpa;


import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.service.animal.AnimalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {

    AnimalEntity findByTypeAnimal(String animalType);
}
