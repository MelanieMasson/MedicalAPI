package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.UserService;
import org.aspectj.weaver.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService us;

    //param page : numéro de la page actuelle
    // size : nbre d'élements par page
    @GetMapping(value = "")
    public String list( Model model, HttpServletRequest request ){
        Iterable<UserEntity> users = us.findAll();

        model.addAttribute("users" , users );
        model.addAttribute( "error" , request.getParameter("error") );
        model.addAttribute( "success" , request.getParameter("success") );

        return "user/list_user";
    }

    // http://localhost:8080/user/add
    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("user" , new UserEntity() );
        return "user/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request , Model model ){
        // Récupération des paramètres envoyés en POST
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String roles = request.getParameter("roles");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String photouser = request.getParameter("photouser");

        // Préparation de l'entité à sauvegarder
        UserEntity u = new UserEntity( 0 , username , email , roles , password , name , photouser);

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            us.addUser( u );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
            model.addAttribute("user" , u );
            model.addAttribute("error" , e.getMessage() );
            return "user/add_edit";
        }
        return "redirect:/user?success=true";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit( Model model , @PathVariable int id ){
        try {
            model.addAttribute("user", us.findUser(id));
        }catch( NoSuchElementException e){
            return "redirect:/user?error=User%20introuvalble";
        }

        return "user/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPost(  Model model , HttpServletRequest request , @PathVariable int id ){
        // Récupération des paramètres envoyés en POST
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String roles = request.getParameter("roles");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String photouser = request.getParameter("photouser");

        // Préparation de l'entité à sauvegarder
        UserEntity u = new UserEntity( 0 , username , email , roles , password , name , photouser);

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            us.editUser( id , u );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
            model.addAttribute( "user" , u );
            model.addAttribute("error" , e.getMessage());
            return "user/add_edit";
        }
        return "redirect:/user?success=true";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete( @PathVariable int id ){
        String message = "?success=true";
        try{
            us.delete(id);
        }catch( Exception e ){
            message = "?error=User%20introuvable";
        }

        return "redirect:/user" + message;
    }

    public UserService getUs() {
        return us;
    }

    public void setUs(UserService us) {
        this.us = us;
    }

}