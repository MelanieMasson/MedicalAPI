package fr.m2i.medical.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ville")
public class VilleController {

    // http://localhost:8080/ville/test
    //@RequestMapping(value = "/test", method = RequestMethod.GET )
    @GetMapping(value = "/test")
    @ResponseBody
    public String testme( ){
        return "<h1>Liste des villes</h1>";
    }


    /*@GetMapping(value = "/delete/{id}")
    public void delete(@PathVariable int id) {
        pr.deleteById(id);
    }*/

}