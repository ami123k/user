package user.sec.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import user.sec.user.repository.OffreRepositiory;
import user.sec.user.storage.StorageService;
import user.sec.user.models.*;

import javax.annotation.Resource;

@CrossOrigin(origins = "*", maxAge = 3600)
@SpringBootApplication
public class UserApplication implements CommandLineRunner {
    @Autowired
    private OffreRepositiory offreRepositiory ;
    @Autowired
    private RepositoryRestConfiguration restConfiguration ;
    @Resource
    StorageService storageService;
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);

    }
    @Override
    public void run(String... args) throws Exception {
        restConfiguration.exposeIdsFor(offre.class);
       // storageService.deleteAll();
        //storageService.init();
        //  produitRepository.save(new produit(1,"ordinateur",670.0,3));
        //produitRepository.save(new produit(2,"ordinateur",670.0,3));
        //produitRepository.save(new produit(6,"ordinateur",670.0,3));
        //    produitRepository.findAll().forEach(p->{System.out.println(p.toString());});
        Object err;
        //  us.save(new User(Roles.Admin,"aaaaazezaea","aaaaaaaaaaaa","aaaaaaaa"));
    }
}
