package fr.m2i.medical.service;
import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VilleService {

    private VilleRepository vr;

    public VilleService( VilleRepository vr ){
        this.vr = vr;
    }

    public Iterable<VilleEntity> findAll(  ) {
        return vr.findAll();
    }

    public Iterable<VilleEntity> findAll(  String search  ) {
        if( search != null && search.length() > 0 ){
            return vr.findByNomContains(search);
        }
        return vr.findAll();
    }

    public VilleEntity findVille(int id) {
        return vr.findById(id).get();
    }

    public void delete(int id) {
        vr.deleteById(id);
    }

    private void checkVille( VilleEntity v ) throws InvalidObjectException {

        if( v.getNom().length() <= 2  ){
            throw new InvalidObjectException("Nom de ville invalide");
        }

        if( v.getPays().length() <= 3  ){
            throw new InvalidObjectException("Nom du pays invalide");
        }

    }

    public void addVille( VilleEntity v ) throws InvalidObjectException {
        checkVille(v);
        vr.save(v);
    }

    public void editVille( int id , VilleEntity v) throws InvalidObjectException , NoSuchElementException {
        checkVille(v);
        try{
            VilleEntity vExistante = vr.findById(id).get();

            vExistante.setCodePostal( v.getCodePostal() );
            vExistante.setNom( v.getNom() );
            vExistante.setPays( v.getPays() );
            vr.save( vExistante );

        }catch ( NoSuchElementException e ){
            throw e;
        }

    }
}