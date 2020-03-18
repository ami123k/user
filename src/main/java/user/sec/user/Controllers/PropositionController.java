package user.sec.user.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import user.sec.user.models.categorie;
import user.sec.user.models.*;
import user.sec.user.repository.OffreRepositiory;
import user.sec.user.repository.PropositionRepository;
import user.sec.user.storage.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PropositionController {
    @Autowired
    private PropositionRepository propositionRepositiory ;
@Autowired
private offreController offreController;
@Autowired
private UserController userController;

    /* ////////////////////////////afficher////////////////////// */

    @GetMapping(value = "/listproposition" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<proposition> propoList(){
        return  propositionRepositiory.findAll();

    }


    /* ////////////////////////////find////////////////////// */
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping(value = "/listpropo/{id}" )
    public proposition proposition(@PathVariable(name = "id") Long id ) {
        return propositionRepositiory.findById(id).get();
    }


    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @RequestMapping(value = "/ajoutpropo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public proposition addpropo(@RequestBody proposition u) {
        System.out.println("(Service Side) Creating equipe : ");
        proposition equipe = propositionRepositiory.save(u);
        return equipe;
    }
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping(value = "/deletepropo/{id}" )
    public void Delete(@PathVariable(name = "id") Long id ) {
        propositionRepositiory.deleteById(id);

    }

    @Autowired
    StorageService storageService;

    List<String> files = new ArrayList<String>();
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/{user_id}/addproposition/{offre_id}")
    public ResponseEntity<String> handleFileUpload(@PathVariable(value = "user_id") Long id_user,@RequestParam("file") MultipartFile file  , String description, @PathVariable(value = "offre_id") Long id_offre ) {
        String message = "";
        try {

            storageService.store(file);
            files.add(file.getOriginalFilename());
            proposition e = new proposition(file.getName(),true,userController.user(id_user),offreController.offre(id_offre),description,file.getContentType(),file.getOriginalFilename());
         ////  offre a = offreController.offre(id_offre);
           // System.out.println(a+"aaaaaaaaaaaaaa");
          // e.setOffre(a);
          //  User u = userController.user(id_user);
           // e.setUser(u);
            propositionRepositiory.save(e);
            System.out.println(file.getOriginalFilename());
            System.out.println(e.getDescription()+"////////////////////////////");
            System.out.println(e.getOffre().getId_offre());
            System.out.println(e.getUser().getUsername());
            message = "You successfully uploaded " + file.getOriginalFilename() + "!";

            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }



    }

    @GetMapping("/getallfilepropo")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<String> getListFiles( ) {
        List<String> fileNames = files
                .stream().map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(EntrepriseController.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());
        return files;


    }

    @GetMapping(value = "/listpropbyoff/{offre_id}" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<proposition> findpropobyoffre( @PathVariable(value = "offre_id") Long id){
        return  propositionRepositiory.findpropositionByoffre(id);

    }



}
