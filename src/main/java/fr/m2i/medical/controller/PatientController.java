package fr.m2i.medical.controller;
import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService ps;

    @Autowired
    private VilleService vs;

    // http://localhost:8080/patient
    @GetMapping(value = "")
    public String list( Model model, HttpServletRequest request ){
        String search = request.getParameter("search");
        Iterable<PatientEntity> patients = ps.findAll(search);
        model.addAttribute("patients" , ps.findAll() );
        model.addAttribute( "error" , request.getParameter("error") );
        model.addAttribute( "success" , request.getParameter("success") );
        model.addAttribute( "search" , search );
        return "patient/list_patient";
    }

    // http://localhost:8080/patient/add
    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("villes" , vs.findAll() );
        model.addAttribute("patient" , new PatientEntity() );
        return "patient/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request, Model model){
        // Récupération des paramètres envoyés en POST
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String naissance = request.getParameter("naissance");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        int ville = Integer.parseInt(request.getParameter("ville"));

        // Préparation de l'entité à sauvegarder
        VilleEntity v = new VilleEntity();
        v.setId(ville);
        PatientEntity p = new PatientEntity( 0 , nom , prenom , Date.valueOf( naissance ) , email , telephone , adresse , v );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            ps.addPatient( p );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
            model.addAttribute("patient" , v );
            model.addAttribute("error" , e.getMessage() );
            return "patient/add_edit";
        }
        return "redirect:/patient";
    }

    @RequestMapping( method = { RequestMethod.GET , RequestMethod.POST} , value = "/edit/{id}" )
    public String editGetPost( Model model , @PathVariable int id ,  HttpServletRequest request ){
        System.out.println( "Add Edit Patient" + request.getMethod() );

        if( request.getMethod().equals("POST") ){
            // Récupération des paramètres envoyés en POST
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String naissance = request.getParameter("naissance");
            String adresse = request.getParameter("adresse");
            String email = request.getParameter("email");
            String telephone = request.getParameter("telephone");
            int ville = Integer.parseInt(request.getParameter("ville"));

            // Préparation de l'entité à sauvegarder
            VilleEntity v = new VilleEntity();
            v.setId(ville);
            PatientEntity p = new PatientEntity( 0 , nom , prenom , Date.valueOf( naissance ) , email , telephone , adresse , v );

            // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
            try{
                ps.editPatient( id , p );
            }catch( Exception e ){
                p.setId(  -1 ); // hack
                System.out.println( e.getMessage() );
                model.addAttribute("patient" , p );
                model.addAttribute("error" , e.getMessage() );
                return "patient/add_edit";
            }
            return "redirect:/patient?success=true";
        }else{
            try{
                model.addAttribute("patient" , vs.findVille( id ) );
            }catch ( NoSuchElementException e ){
                return "redirect:/patient?error=Patient%20introuvalble";
            }

            return "patient/add_edit";
        }
    }

    @GetMapping(value = "/edit/{id}")
    public String edit( Model model , @PathVariable int id ){
        model.addAttribute("villes" , vs.findAll() );
        model.addAttribute("patient" , ps.findPatient( id ) );
        return "patient/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPost( HttpServletRequest request , @PathVariable int id ){
        // Récupération des paramètres envoyés en POST
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String naissance = request.getParameter("naissance");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        int ville = Integer.parseInt(request.getParameter("ville"));

        // Préparation de l'entité à sauvegarder
        VilleEntity v = new VilleEntity();
        v.setId(ville);
        PatientEntity p = new PatientEntity( 0 , nom , prenom , Date.valueOf( naissance ) , email , telephone , adresse , v );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            ps.editPatient( id , p );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }
        return "redirect:/patient";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete( @PathVariable int id ){
        String message = "?success=true";
        try{
            ps.delete(id);
        }catch ( Exception e ){
            message = "?error=Patient%20introuvalble";
        }
        return "redirect:/patient"+message;
    }

    public PatientService getPservice() {
        return ps;
    }

    public void setPservice(PatientService ps) {
        this.ps = ps;
    }

}