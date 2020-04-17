package user.sec.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import user.sec.user.models.*;

import java.util.List;
import java.util.Optional;

public interface EntrepriseRepository extends JpaRepository<entreprise, Long> {
    @Query("SELECT u.categories FROM entreprise u WHERE u.id_entreprise = ?1")
    public categorie findcat(Long id);
    Optional<entreprise> findByName(String name);

    @Query("SELECT u FROM entreprise u where u.categories = ?1")
    public List<entreprise> finentrepriseBycat(categorie cat);

    @Query("SELECT count (u) FROM entreprise u ")
    public int countentreprise();
    @Query("SELECT u.entrepriseuser.name_entreprise FROM User u where u.id = ?1")
    public String finentreprisebyuser(long id);


}
