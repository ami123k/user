package user.sec.user.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import user.sec.user.models.categorie;
import user.sec.user.models.*;
import user.sec.user.repository.*;
import user.sec.user.sftp.UpAndDownload;
import user.sec.user.storage.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PropositionController {
    @Autowired
    private PropositionRepository propositionRepositiory ;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
private offreController offreController;
@Autowired
private UserController userController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntrepriseController entrepriseController;
    /* ////////////////////////////afficher////////////////////// */
    @Autowired
    private EntrepriseRepository entrepriseRepository ;

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

    @GetMapping(value = "/listentreprisebypropo/{id_proposition}" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<String> findusersfromproposition( @PathVariable(value = "id_proposition") Long id){
        return  propositionRepositiory.findusersfromproposition(id);

    }
    @GetMapping(value = "/finduserfromproposition/{id_proposition}" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<proposition> finduserfromproposition( @PathVariable(value = "id_proposition") Long id){
        return  propositionRepositiory.finduserfromproposition(id);

    }
    @GetMapping(value = "/findpropositionByid/{proposition_id}" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public proposition findpropositionByid( @PathVariable(value = "proposition_id") Long id){
        return  propositionRepositiory.findpropositionByid(id);

    }

    @GetMapping(value = "/nameentreprisebypropo/{id_proposition}" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<String> nameentreprisebypropo( @PathVariable(value = "id_proposition") Long id){
        return  propositionRepositiory.nameentreprisebypropo(id);

    }

    @GetMapping(value = "/maxid" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Long maxid(){
        return  propositionRepositiory.maxidentreprise();

    }
    Set<Role> roles = new HashSet<>();
    /* ////////////////////////////update////////////////////// */
    @RequestMapping(value = "/Updateuser/{id}", method = RequestMethod.PUT)
    public User Updateuser(@PathVariable(name = "id") Long id , @RequestBody User p) {
        Role userRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        p.setId(id);
        User user = userController.user(id);
        p.setEmail(user.getEmail());
        p.setPassword(user.getPassword());
        p.setUsername(user.getUsername());
        p.setValider(true);
        p.setEntrepriseuser(entrepriseRepository.findById(maxid()).get());
        p.setRoles(roles);
        return userRepository.save(p);

    }
    @RequestMapping(value = "/Updateadmin/{id}", method = RequestMethod.PUT)
    public User valider(@PathVariable(name = "id") Long id , @RequestBody User p) {
        Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        p.setValider(true);
        p.setId(id);
        User user = userController.user(id);
        p.setEmail(user.getEmail());
        p.setPassword(user.getPassword());
        p.setUsername(user.getUsername());
        p.setRoles(roles);
        return userRepository.save(p);

    }

    @RequestMapping(value = "/Updateproposition/{id}", method = RequestMethod.PUT)
    public proposition validerpropostion(@PathVariable(name = "id") Long id , @RequestBody proposition p) {
        p.setId_proposition(id);
        proposition propo = propositionRepositiory.findById(id).get();
        p.setDescription(propo.getDescription());
        p.setName(propo.getName());
        p.setPropositionscanner(propo.getPropositionscanner());
        p.setType(propo.getType());
        p.setUser(propo.getUser());
        p.setOffre(propo.getOffre());
        p.setValidation(false);
        return propositionRepositiory.save(p);

    }

    @GetMapping(value = "/listpropbyoffconfirm/{user_id}" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<proposition> findBypropoconfirm( @PathVariable(value = "user_id") Long user_id){
        return  propositionRepositiory.findBypropoconfirm(user_id,false);

    }
    @GetMapping(value = "/findpropouserbyoffre/{user_id}/{offre_id}" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<proposition> findpropouserbyoffre( @PathVariable(value = "user_id") Long user_id ,@PathVariable(value = "offre_id") Long offre_id){
        return  propositionRepositiory.findpropouserbyoffre(user_id,offre_id);

    }

}
