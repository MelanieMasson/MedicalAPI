package fr.m2i.medical.service;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.UserRepository;
import fr.m2i.medical.repositories.UserRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private UserRepository ur;

    public UserService(UserRepository ur){
        this.ur = ur;
    }

    public Iterable<UserEntity> findAll() {
        return ur.findAll();
    }

    public Iterable<UserEntity> findAll( String search ) {
        return ur.findAll();
    }

    public UserEntity findUser(int id) {
        return ur.findById(id).get();
    }

    public void delete(int id) {
        ur.deleteById(id);
    }

    public static boolean validate(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private void checkUser( UserEntity u ) throws InvalidObjectException {

        if( u.getUsername().length() <= 2 ){
            throw new InvalidObjectException("Nom d'utilisateur invalide");
        }

        if( u.getName().length() <= 2 ){
            throw new InvalidObjectException("Nom invalide");
        }

        if( u.getEmail().length() <= 5 || !validate( u.getEmail() ) ){
            throw new InvalidObjectException("Email invalide");
        }
    }

    public void addUser(UserEntity u) throws InvalidObjectException {
        checkUser(u);
        ur.save(u);
    }

    public void editUser(int id, UserEntity u) throws InvalidObjectException {
        checkUser(u);

        /*Optional<UserEntity> pe = ur.findById(id);
        UserEntity pp = pe.orElse( null );*/

        try{
            UserEntity uExistant = ur.findById(id).get();

            uExistant.setUsername( u.getUsername() );
            uExistant.setEmail( u.getEmail() );
            uExistant.setRoles( u.getRoles() );
            uExistant.setPassword( u.getPassword() );
            uExistant.setName( u.getName() );
            uExistant.setPhotouser( u.getPhotouser() );

            ur.save( uExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }
    }
}