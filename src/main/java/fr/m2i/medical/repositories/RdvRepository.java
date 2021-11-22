package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.RdvEntity;
import org.springframework.data.repository.CrudRepository;

public interface RdvRepository extends CrudRepository<RdvEntity, Integer> {

    Iterable<RdvEntity> findByPatient( Integer patient ); // select * from rdv where patient = :patient
    Iterable<RdvEntity> findByPatientContains( Integer patient ); // select * from rdv where patient = :patient

    Iterable<RdvEntity> findByDate( String dateheure ); // select * from rdv where nom like :nom
    Iterable<RdvEntity> findByDateContains( String dateheure ); // select * from rdv where nom like :nom

    Iterable<RdvEntity> findByPatientContainsOrDateContains(String patient , String dateheure );
    // select * from rdv where nom like :nom or prenom like :prenom


}