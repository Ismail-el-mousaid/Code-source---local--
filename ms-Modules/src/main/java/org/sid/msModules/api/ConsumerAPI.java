package org.sid.msModules.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sid.msModules.entities.*;
import org.sid.msModules.entities.Module;
import org.sid.msModules.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@Order(1)
public class ConsumerAPI {

    @Autowired
    private DeploiementRepository deploiementRepository;
    @Autowired
    private BrancheRepository brancheRepository;
    @Autowired
    private EnvironnementRepository environnementRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ConsumerTags consumerTags;


    public void getInfosDeploiements() throws ParseException {

        System.out.println("-----------------");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
       // String token = "glpat-Vie8uuParSMECxyrnhxw";
        String token = "glpat-B5opyUQDLEHK3SGC_w7A";
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        // Get Toutes Les Modules d'un Groupe

        System.out.println("---------Toutes Les Modules---------");

        String url = "https://gitlab.com/api/v4/groups/referentiel_deploiement";
        ResponseEntity<Object> responseModules = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        HashMap<String, Object> hashMapModules = (HashMap<String, Object>) responseModules.getBody();
        JSONObject jsonObjectModules = new JSONObject(hashMapModules);

        JSONArray jsonArrayModulesProjects = jsonObjectModules.getJSONArray("projects");
        System.out.println(jsonArrayModulesProjects);
        for (int i = 0; i < jsonArrayModulesProjects.length(); i++) {
            System.out.println("---------Module "+i+"-------------------------------");
            JSONObject jsonObjectModule = jsonArrayModulesProjects.getJSONObject(i);
            Long idModule = jsonObjectModule.getLong("id");
            System.out.println(idModule);
            String nameModule = jsonObjectModule.getString("name");
            System.out.println(nameModule);
            Module module = new Module();
            module.setId(idModule);
            module.setName(nameModule);
            moduleRepository.save(module);


            System.out.println("--------------------");

            String urlDeplo = "https://gitlab.com/api/v4/projects/" + idModule + "/deployments/";
            ResponseEntity<Object> responseDeploiements = restTemplate.exchange(urlDeplo, HttpMethod.GET, entity, Object.class);
            if (responseDeploiements != null) {
                ArrayList<Object> arrayListDeploiements = new ArrayList<>((Collection<?>) responseDeploiements.getBody());
                arrayListDeploiements.stream().forEach(deplo -> {
                    HashMap<String, Object> hashMapDeploiement = (HashMap<String, Object>) deplo;
                    JSONObject jsonObjectDeploiement = new JSONObject(hashMapDeploiement);
                    System.out.println(hashMapDeploiement.get("deployable"));
                    if (jsonObjectDeploiement.getString("status").equals("success") && hashMapDeploiement.get("deployable") != null) {
                        /*--------Get Branche---------*/
                        //Get Id Branche
                        Long idBranche = jsonObjectDeploiement.getLong("id");
                        System.out.println("id Branche : " + idBranche);
                        //Get Name Branche
                        String nameBranche = jsonObjectDeploiement.getString("ref");
                        System.out.println("name Branche : " + nameBranche);

                        /*---------Get Env------------*/
                        JSONObject jsonObjectEnv = jsonObjectDeploiement.getJSONObject("environment");
                        //Get Id Env
                        Long idEnv = jsonObjectEnv.getLong("id");
                        System.out.println("id Env : " + idEnv);
                        //Get Name Env
                        String nameEnv = jsonObjectEnv.getString("name");
                        System.out.println("name Env : " + nameEnv);

                        /*---------Get Deploiement--------*/
                        JSONObject jsonObjectDeployable = jsonObjectDeploiement.getJSONObject("deployable");
                        // Get Date déploiement
                        String dateDeploiement = jsonObjectDeployable.getString("finished_at");
                        // Convert String -> Date
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateDep = null;
                        Date parsedDate = null;
                        try {
                            dateDep = formatter.parse(String.valueOf(dateDeploiement));
                            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                                    Locale.ENGLISH);
                            parsedDate = sdf.parse(String.valueOf(dateDep));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
                        String dateDeploiemenetFormat = print.format(parsedDate);
                        System.out.println("Date Déploiement : " + dateDeploiemenetFormat);

                        /*----------Get User----------*/
                        JSONObject jsonObjectUser = jsonObjectDeployable.getJSONObject("user");
                        // Get Name User
                        String nameEmploye = jsonObjectUser.getString("name");
                        System.out.println("name Employe : " + nameEmploye);

                        /*-------Get Pipeline-------*/
                        JSONObject jsonObjectPipeline = jsonObjectDeployable.getJSONObject("pipeline");
                        //Get version
                        Long version = jsonObjectPipeline.getLong("id");
                        System.out.println("version : " + version);

                        /*-------Get Objet Commit--------*/
                        JSONObject jsonObjectCommit = jsonObjectDeployable.getJSONObject("commit");
                        //Get commit
                        String commit = jsonObjectCommit.getString("short_id");
                        System.out.println("commit : " + commit);
                        //Get message
                        String messageCommit = jsonObjectCommit.getString("message");
                        System.out.println("message : " + messageCommit);

                        /*--------Get Release Note------------*/
                        String releaseNote = consumerTags.getReleaseNote(restTemplate, entity, version);
                        System.out.println("release Note : " + releaseNote);

                        /*-------------*/

                        /*-----Save Branche------*/
                        Branche branche = new Branche();
                        branche.setId(idBranche);
                        branche.setName(nameBranche);
                        branche.setModule(module);
                        brancheRepository.save(branche);

                        /*-----Save Env---------*/
                        Environnement environnement = new Environnement();
                        environnement.setId(idEnv);
                        environnement.setName(nameEnv);
                        environnementRepository.save(environnement);

                        /*--------Save Deploiement--------*/
                        Deploiement deploiement = new Deploiement();
                        deploiement.setVersion(String.valueOf(version));
                        try {
                            deploiement.setDateDeploiement(formatter.parse(dateDeploiemenetFormat));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        deploiement.setNameEmploye(nameEmploye);
                        deploiement.setReleaseNote(releaseNote);
                        deploiement.setBranche(branche);
                        deploiement.setEnvironnement(environnement);
                        deploiementRepository.save(deploiement);
                    }

                });

            }

        }
    }

}
