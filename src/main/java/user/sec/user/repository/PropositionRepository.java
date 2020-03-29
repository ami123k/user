package user.sec.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import user.sec.user.models.*;

import java.util.List;

@Component
@RepositoryRestResource
@CrossOrigin("*")
public interface PropositionRepository extends JpaRepository<proposition, Long> {
    @Query("SELECT u FROM proposition u where u.offre.id_offre = ?1")
    public List<proposition> findpropositionByoffre(Long cat);
   @Query("SELECT u.user.entrepriseuser.name_entreprise FROM proposition u where u.offre.id_offre = ?1")
    public List<String> findusersfromproposition(Long cat);

    @Query("SELECT u FROM proposition u where u.id_proposition = ?1")
    public proposition findpropositionByid(Long cat);

    @Query("SELECT u.offre.name FROM proposition u where u.id_proposition = ?1")
    public offre findoffrebyproposition(Long cat);

    @Query("SELECT u.user.entrepriseuser.name_entreprise FROM proposition u where u.id_proposition = ?1")
    public List<proposition> findprop(Long cat);

    @Query("SELECT u,u.user.entrepriseuser, u.offre FROM proposition u where u.id_proposition = ?1")
    public List<Object> findpropositionoffreentreByid(Long id);

    @Query(value ="SELECT * FROM propositions u where u.offre_id = ?1" , nativeQuery = true)
    public List<proposition> finduserfromproposition(Long cat);


    @Query("SELECT MAX(id_entreprise) FROM entreprise u " )
    public Long maxidentreprise();

    @Query("SELECT u.user.entrepriseuser.name_entreprise FROM proposition u where u.id_proposition = ?1")
    public List<String> nameentreprisebypropo(Long cat);



 @Query("SELECT e from proposition e where e.user.id =:id AND e.validation =:validation")
 List<proposition> findBypropoconfirm(@Param("id") Long id,@Param("validation") boolean role);


 @Query("SELECT e from proposition e where e.user.id =:id AND e.offre.id_offre =:id_offre")
 List<proposition> findpropouserbyoffre(@Param("id") Long id,@Param("id_offre") Long id_offre);
}
