package org.sid.msModules.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sid.msModules.api.ConsumerAPI;
import org.sid.msModules.api.ConsumerTags;
import org.sid.msModules.dto.BrancheDTO;
import org.sid.msModules.dto.DeploiementDTO;
import org.sid.msModules.dto.EnvironnementDTO;
import org.sid.msModules.dto.ModuleDTO;
import org.sid.msModules.entities.*;
import org.sid.msModules.entities.Module;
import org.sid.msModules.exceptions.BrancheNotFoundException;
import org.sid.msModules.map.MapStructMapper;
import org.sid.msModules.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
@Order(1)
public class ModuleServiceImpl implements IModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private BrancheRepository brancheRepository;
    @Autowired
    private EnvironnementRepository environnementRepository;
    @Autowired
    private DeploiementRepository deploiementRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MapStructMapper mapStructMapper;
    @Autowired
    private ConsumerTags consumerTags;
    @Autowired
    private ConsumerAPI consumerAPI;


    @Override
    public BrancheDTO getBranche(Long id) throws BrancheNotFoundException {
        BrancheDTO brancheDTO = mapStructMapper.mapToBrancheDTO(brancheRepository.findById(id).orElse(null)); //Si tu ne trouve pas, affecte null
        if (brancheDTO == null)
            throw new BrancheNotFoundException("Branche not found");
        return brancheDTO;
    }

    @Override
    public List<BrancheDTO> getBranchesByNameBrancheAndNameModule(String nameBranche, String nameModule, Pageable pageable) {
        return mapStructMapper.mapToListBranchesDTO(brancheRepository.findByNameContainsAndModuleNameContains(nameBranche, nameModule, pageable).getContent());
    }

    @Override
    public List<ModuleDTO> getModules(String name, Pageable pageable) {
        return mapStructMapper.mapToListModulesDTO(moduleRepository.findByNameContains(name, pageable).getContent());
    }

    @Override
    public ModuleDTO getModule(Long id) {

        return mapStructMapper.mapToModuleDTO(moduleRepository.findById(id).get());
    }

    @Override
    public List<EnvironnementDTO> getEnvironnements(String name, Pageable pageable) {
        return mapStructMapper.mapToListEnvironnementsDTO(environnementRepository.findByNameContains(name, pageable).getContent());
    }

    @Override
    public EnvironnementDTO getEnvironnement(Long id) {
        return mapStructMapper.mapToEnvironnementDTO(environnementRepository.findById(id).get());
    }

    @Override
    public List<DeploiementDTO> getDeploiements(String branche, Pageable pageable) {
        return mapStructMapper.mapToListDeploiementsDTO(deploiementRepository.findByBrancheNameContains(branche, pageable).getContent());
    }

    @Override
    public List<DeploiementDTO> getDeploiementsByBranche(Long idBranche) {
        return mapStructMapper.mapToListDeploiementsDTO(deploiementRepository.findByBrancheId(idBranche));
    }

    @Override
    public List<DeploiementDTO> getDeploiementsByEnv(Long idEnvironnement, Pageable pageable) {
        return mapStructMapper.mapToListDeploiementsDTO(deploiementRepository.findByEnvironnementId(idEnvironnement, pageable).getContent());
    }

    @Override
    public Page<Deploiement> getDeploiementsByBrancheAndModuleAndEnv(String nameBranche, String nameModule, String nameEnv, Pageable pageable) {
        return deploiementRepository.findByBrancheNameContainsAndBrancheModuleNameContainsAndEnvironnementNameContains(nameBranche, nameModule, nameEnv, pageable);
    }

    @Override
    public Page<Deploiement> getDeploiementsByBrancheAndModuleAndEnvAndDateDeploiement(String nameBranche, String nameModule, String nameEnv, Date dateDeploiement, Pageable pageable) {
        return deploiementRepository.findByBrancheNameContainsAndBrancheModuleNameContainsAndEnvironnementNameContainsAndDateDeploiement(nameBranche, nameModule, nameEnv, dateDeploiement, pageable);
    }

    @Override
    public Page<Deploiement> getDeploiementsByBrancheAndModuleAndEnvAndTriAsc(String nameBranche, String nameModule, String nameEnv, Pageable pageable) {
        return deploiementRepository.findByBrancheNameContainsAndBrancheModuleNameContainsAndEnvironnementNameContainsOrderByDateDeploiementAsc(nameBranche, nameModule, nameEnv, pageable);
    }

    @Override
    public Page<Deploiement> getDeploiementsByBrancheAndModuleAndEnvAndTriDesc(String nameBranche, String nameModule, String nameEnv, Pageable pageable) {
        return deploiementRepository.findByBrancheNameContainsAndBrancheModuleNameContainsAndEnvironnementNameContainsOrderByDateDeploiementDesc(nameBranche, nameModule, nameEnv, pageable);
    }

    @Override
    public Page<Deploiement> getDeploiementsByDateDeploiement(Date dateDebut, Date dateFin, Pageable pageable) {
        return deploiementRepository.getByDateDeploiement(dateDebut, dateFin, pageable);
    }

    @Override
    public void getInfosFromAPI() throws ParseException {
        consumerAPI.getInfosDeploiements();
    }

    @Override
    public Page<Tag> getTags(Pageable pageable) {
        consumerTags.getTags();
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }


    /*---------Runner----------*/
    private final AtomicBoolean done = new AtomicBoolean();
    @Override
    public void run(Runnable task) {
        if (done.get()) return;
        if (done.compareAndSet(false, true)) {
            task.run();
        }
    }

    /*---------Live Deplyment--------*/
    @Override
    public Date getRecentDateDeploiement(List<Date> datesList) {
        Collections.sort(datesList);
        Date recentDate = datesList.get(datesList.size() - 1);
        return recentDate;
    }

    @Override
    public List<String> getDatesDeploiements() {
        System.out.println("--------getDatesDeploiements----------");

        List<String> datesDeploiementsList = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);



        String url = "http://localhost:8888/MS-MODULES/deploiements";
        ResponseEntity<Object> responseModules = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        HashMap<String, Object> hashMapModules = (HashMap<String, Object>) responseModules.getBody();
        JSONObject jsonObjectModules = new JSONObject(hashMapModules);

        JSONArray jsonArrayModulesProjects = jsonObjectModules.getJSONArray("projects");
        System.out.println(jsonArrayModulesProjects);
        for (int i = 0; i < jsonObjectModules.length(); i++) {
            System.out.println("---------Module " + i + "-------------------------------");
            JSONObject jsonObjectModule = jsonObjectModules.getJSONObject(String.valueOf(i));
            String dateDeploy = jsonObjectModule.getString("dateDeploiement");
          //  String nameModule = jsonObjectModule.getString("name");
            System.out.println(dateDeploy);
            datesDeploiementsList.add(dateDeploy);

        }

        return datesDeploiementsList;
    }


}
