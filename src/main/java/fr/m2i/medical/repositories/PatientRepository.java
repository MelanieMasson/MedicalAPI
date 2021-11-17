package fr.m2i.medical.repositories;
import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEntity , Integer> {

    public List<PatientEntity> findByNomContains(String search );

}