package user.sec.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.sec.user.Controllers.PropositionController;
import user.sec.user.models.*;
import user.sec.user.models.statistiqueEntreprise;
import user.sec.user.repository.PropositionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class propositionServiceImp implements propositionService{


  @Autowired
  PropositionRepository propositionRepository ;
@Autowired
    PropositionController propositionController;
    public List<propositionentreprise> propositionentreprises(Long u)
    {
        List<propositionentreprise> propo=new ArrayList<>();
        List <proposition> Users=(List<proposition>) propositionController.findpropobyoffre(u);
        for(proposition e:Users)
        {
            propositionentreprise stat =new propositionentreprise();
            stat.setDescription(e.getDescription());
            stat.setId_proposition(e.getId_proposition());
            stat.setName(e.getOffre().getName());
            stat.setPropositionscanner(e.getName());
            stat.setValidation(e.isValidation());
            stat.setName_entreprise(e.getUser().getEntrepriseuser().getName_entreprise());
            stat.setImage_entreprise(e.getUser().getEntrepriseuser().getType());
            propo.add(stat);
        }

        for(propositionentreprise b :propo){

            System.out.println(b.getImage_entreprise());
        }
        return propo;
    }
}
