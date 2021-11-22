package fr.m2i.medical.controller;


import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.RdvService;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/rdv")
public class RdvController {

    @Autowired
    private RdvService rs;

    @Autowired
    private PatientService ps;

    // http://localhost:8080/rdv
    @GetMapping(value = "")
    public String list( Model model , HttpServletRequest req ){
        String search = req.getParameter("search");
        model.addAttribute("rdv" , rs.findAll( search ) );
        model.addAttribute("error" , req.getParameter("error") );
        model.addAttribute("success" , req.getParameter("success") );
        model.addAttribute("search" , search );
        return "rdv/list_rdv";
    }

    // http://localhost:8080/rdv/add
    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("patients" , ps.findAll() );
        model.addAttribute("rdv" , new RdvEntity() );
        return "rdv/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request , Model model ){
        // Récupération des paramètres envoyés en POST
        Integer patient = Integer.parseInt(request.getParameter("patient"));
        String dateheure = request.getParameter("dateheure");
        Integer duree = Integer.parseInt(request.getParameter("duree"));
        String note = request.getParameter("note");
        String type = request.getParameter("type");

        // Préparation de l'entité à sauvegarder
        PatientEntity p = new PatientEntity();
        p.setId(patient);
        RdvEntity r = new RdvEntity( 0 , p , dateheure , duree , note , type );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            rs.addRdv( r );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
            model.addAttribute("rdv" , r );
            model.addAttribute("patients" , ps.findAll() );
            model.addAttribute("error" , e.getMessage() );
            return "rdv/add_edit";
        }
        return "redirect:/rdv?success=true";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit( Model model , @PathVariable int id ){
        model.addAttribute("patients" , ps.findAll() );
        try {
            model.addAttribute("rdv", rs.findRdv(id));
        }catch( NoSuchElementException e){
            return "redirect:/rdv?error=Rdv%20introuvalble";
        }

        return "rdv/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPost(  Model model , HttpServletRequest request , @PathVariable int id ){
        // Récupération des paramètres envoyés en POST
        int patient = Integer.parseInt(request.getParameter("patient"));
        String dateheure = request.getParameter("dateheure");
        int duree = Integer.parseInt(request.getParameter("duree"));
        String note = request.getParameter("note");
        String type = request.getParameter("type");

        // Préparation de l'entité à sauvegarder
        PatientEntity p = new PatientEntity();
        p.setId(patient);
        RdvEntity r = new RdvEntity( 0 , p , dateheure , duree , note , type );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            rs.editRdv( id , r );
        }catch( Exception e ){
            model.addAttribute("patients" , ps.findAll() );
            System.out.println( e.getMessage() );
            model.addAttribute( "rdv" , r );
            model.addAttribute("error" , e.getMessage());
            return "rdv/add_edit";
        }
        return "redirect:/rdv?success=true";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete( @PathVariable int id ){
        String message = "?success=true";
        try{
            rs.delete(id);
        }catch( Exception e ){
            message = "?error=Rdv%20introuvable";
        }

        return "redirect:/rdv" + message;
    }

    public RdvService getRs() {return rs;}

    public void setRs(RdvService rs) {this.rs = rs;}

}