package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class PatientService {

    private PatientRepository pr;
    private VilleService vs;

    public PatientService( PatientRepository pr, VilleService vs ){
        this.pr = pr;
        this.vs = vs;
    }

    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
    }

    private void checkPatient( PatientEntity p ) throws InvalidObjectException {

        if( p.getNom().length() <= 2  ){
            throw new InvalidObjectException("Nom de patient invalide");
        }

        if( p.getPrenom().length() <= 2  ){
            throw new InvalidObjectException("Prénom du patient invalide");
        }

        String emailtype = "^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$";
        if ((p.getAdresse().matches( emailtype) != true ) || (p.getAdresse().length() <= 10)){
            throw new InvalidObjectException("Adresse mail du patient invalide");
        }

        try {
            p.setVille(vs.findVille(p.getVille().getId()));
        } catch (Exception e) {
            throw new InvalidObjectException("Ville du patient invalide");
        }

    }

    public PatientEntity findPatient(int id) {
        return pr.findById(id).get();
    }

    public void addPatient( PatientEntity v ) throws InvalidObjectException {
        checkPatient(v);
        pr.save(v);
    }

    public void delete(int id) {
        pr.deleteById(id);
    }

    public void editPatient( int id , PatientEntity p) throws InvalidObjectException , NoSuchElementException {
        checkPatient(p);
        try{
            PatientEntity pExistante = pr.findById(id).get();

            pExistante.setNom( p.getNom() );
            pExistante.setPrenom( p.getPrenom() );
            pExistante.setDatenaissance(p.getDatenaissance());
            pExistante.setAdresse(p.getAdresse());
            pExistante.setVille(p.getVille());
            pr.save( pExistante );

        }catch ( NoSuchElementException e ){
            throw e;
        }

    }
}