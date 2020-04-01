package user.sec.user.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import user.sec.user.repository.OffreRepositiory;
import user.sec.user.models.*;

import java.util.List;
@RestController
@RequestMapping("/offre")
@CrossOrigin(origins = "*", maxAge = 3600L)

public class offreController {
@Autowired
    private OffreRepositiory offreRepositiory ;


    /* ////////////////////////////afficher////////////////////// */
    @GetMapping(value = "/listoffre" )
    public List<offre> produitList(){
        return  offreRepositiory.findAll();

    }
    /////////////////findoffrebycateg////////////////
    @GetMapping(value = "/listoffrebycat" )
    public List<offre> findoffrebycat(categorie categorie){
        return  offreRepositiory.findoffresBycat(categorie);

    }
    @GetMapping(value = "/countoffre" )
    public int countoffre(){
        return  offreRepositiory.countoffre();

    }

    /* ////////////////////////////find////////////////////// */
    @GetMapping(value = "/offre/{id}" )
    public offre offre(@PathVariable(name = "id") Long id ) {
        return offreRepositiory.findById(id).get();
    }




    /* ////////////////////////////update////////////////////// */
    @RequestMapping(value = "/Update/{id}", method = RequestMethod.PUT)
    public offre Updateoffre(@PathVariable(name = "id") Long id ,@RequestBody offre p) {
        System.out.println(offreRepositiory.findcategoriebyoffre(id));
        System.out.println(offreRepositiory.findcat(id));
        p.setCategorie(offreRepositiory.findcat(id));
        p.setId_offre(id);
        p.setCategorie(offreRepositiory.findcat(id));

        return offreRepositiory.save(p);

    }

    @RequestMapping(value = "/ajout", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public offre addEquipe(@RequestBody offre u) {
        System.out.println("(Service Side) Creating equipe : ");
        offre equipe = offreRepositiory.save(u);
        return equipe;
    }

    @DeleteMapping(value = "/delete/{id}" )
    public void Delete(@PathVariable(name = "id") Long id ) {
        offreRepositiory.deleteById(id);

    }





    ////////////////////zeyda classs//////////////////

    /*    @Autowired
    CategorieRepository categorieRepository;


 @PostMapping(value = "/ajout" )
    public ResponseEntity<?> save(@RequestBody categorierequest signUpRequest){
        Set<String> strRoles = signUpRequest.getCategories();
        Set<Ecategorie> Ecategories = new HashSet<>();
        offre offre = new offre(signUpRequest.getName(),
                signUpRequest.getQuantite(),signUpRequest.getDescription());
        offreRepositiory.save(offre);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

        if (strRoles == null) {
            Ecategorie userRole = categorieRepository.findByName(categorie.materiel)
                    .orElseThrow(() -> new RuntimeException("Error: categorie is not found."));
            Ecategories.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "fourniture":
                        Ecategorie fournitureRole = categorieRepository.findByName(categorie.fourniture)
                                .orElseThrow(() -> new RuntimeException("Error: categorie is not found."));
                        Ecategories.add(fournitureRole);
                        break;
                    case "informatique":
                        Ecategorie modRole =categorieRepository.findByName(categorie.informatique)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        Ecategories.add(modRole);

                        break;
                    case "education":
                        Ecategorie education =categorieRepository.findByName(categorie.education)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        Ecategories.add(education);

                        break;
                    default:
                        Ecategorie materiel = categorieRepository.findByName(categorie.materiel)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        Ecategories.add(materiel);
                }
            });
        }*/
    //offre.setCategories(Ecategories);

    //}
}
