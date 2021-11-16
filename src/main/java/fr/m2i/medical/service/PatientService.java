package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PatientService {

    private PatientRepository pr;
    private VilleRepository vr;

    public PatientService(PatientRepository pr, VilleRepository vr ){
        this.pr = pr;
        this.vr = vr;
    }

    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
    }

    public static boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateTelephone(String telephoneStr) {
        Pattern VALID_TELEPHONE_NUMBER_REGEX =
                Pattern.compile("\\d{10} | " +"(?:\\d{3}-){2}\\d{4}|" + "(?:\\d{2}-){4}\\d{2}|" + "(?:\\d{2} ){4}\\d{2}|" + "\\(\\d{3}\\)\\d{3}-?\\d{4}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_TELEPHONE_NUMBER_REGEX.matcher(telephoneStr);
        return matcher.find();
    }

    public PatientEntity findPatient(int id) {
        return pr.findById(id).get();
    }

    public void delete(int id) {
        pr.deleteById(id);
    }

    private void checkPatient( PatientEntity p ) throws InvalidObjectException {

        if( p.getPrenom().length() <= 2 ){
            throw new InvalidObjectException("Prénom invalide");
        }

        if( p.getNom().length() <= 2 ){
            throw new InvalidObjectException("Nom invalide");
        }

        if( p.getAdresse().length() <= 10 ){
            throw new InvalidObjectException("Adresse invalide");
        }

        VilleEntity ve = vr.findById(p.getVille().getId()).orElseGet( null );
        if( ve == null ){
            throw new InvalidObjectException("Ville invalide");
        }

        if( p.getEmail().length() <= 4 || !validateEmail( p.getEmail() ) ){
            throw new InvalidObjectException("Email invalide");
        }

        if( p.getTelephone().length() < 10 || !validateTelephone( p.getTelephone() ) ){
            throw new InvalidObjectException("Numero de téléphone invalide");
        }
    }

    public void addPatient( PatientEntity p ) throws InvalidObjectException {
        checkPatient(p);
        pr.save(p);
    }

    public void editPatient( int id , PatientEntity p) throws InvalidObjectException , NoSuchElementException {
        checkPatient(p);
        try{
            PatientEntity pExistant = pr.findById(id).get();

            pExistant.setNom( p.getNom() );
            pExistant.setPrenom( p.getPrenom() );
            pExistant.setDatenaissance(p.getDatenaissance());
            pExistant.setAdresse(p.getAdresse());
            pExistant.setVille(p.getVille());
            pExistant.setEmail(p.getEmail());
            pExistant.setTelephone(p.getTelephone());
            pr.save( pExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }

        pr.save(p);

    }
}