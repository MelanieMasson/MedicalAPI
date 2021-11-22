package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.RdvRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class RdvService {

    private RdvRepository rr;
    private PatientRepository pr;

    public RdvService(RdvRepository rr, PatientRepository pr ){
        this.rr = rr;
        this.pr = pr;
    }

    public Iterable<RdvEntity> findAll() {
        return rr.findAll();
    }

    public Iterable<RdvEntity> findAll( String search ) {
        if( search != null && search.length() > 0 ){
            return rr.findByPatientContainsOrDateContains( search , search );
        }
        return rr.findAll();
    }

    public RdvEntity findRdv(int id) {
        return rr.findById(id).get();
    }

    public void delete(int id) {
        rr.deleteById(id);
    }

    private void checkRdv( RdvEntity r ) throws InvalidObjectException {

        if( r.getDuree() < 15 ){
            throw new InvalidObjectException("Durée invalide (15min minimum)");
        }

        PatientEntity pe = pr.findById(r.getPatient().getId()).orElseGet( null );
        if( pe == null ){
            throw new InvalidObjectException("Patient invalide");
        }

    }

    public void addRdv(RdvEntity r) throws InvalidObjectException {
        checkRdv(r);
        rr.save(r);
    }

    public void editRdv(int id, RdvEntity r) throws InvalidObjectException {
        checkRdv(r);

        /*Optional<RdvEntity> pe = rr.findById(id);
        RdvEntity pp = pe.orElse( null );*/

        try{
            RdvEntity rExistant = rr.findById(id).get();

            rExistant.setPatient( r.getPatient() );
            rExistant.setDateheure( r.getDateheure() );
            rExistant.setDuree( r.getDuree() );
            rExistant.setNote( r.getNote() );
            rExistant.setType( r.getType() );

            rr.save( rExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }
    }
}