package fr.m2i.medical.repositories;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}