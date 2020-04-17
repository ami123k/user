package user.sec.user.Service;

import user.sec.user.models.statistiqueEntreprise;
import user.sec.user.models.statistiqueOffre;

import java.util.List;

public interface EntrepriseService
{
    public List<statistiqueEntreprise> statequipesujet();
    public List<statistiqueOffre> stateoffre();
}
