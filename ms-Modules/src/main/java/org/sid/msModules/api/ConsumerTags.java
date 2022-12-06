package org.sid.msModules.api;


import org.json.JSONObject;
import org.sid.msModules.entities.Tag;
import org.sid.msModules.repository.DeploiementRepository;
import org.sid.msModules.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@Order(1)
public class ConsumerTags {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private DeploiementRepository deploiementRepository;


    public String getReleaseNote(RestTemplate restTemplate, HttpEntity<String> entity, Long version) {
        String url = "https://gitlab.com/api/v4/projects/36814198/repository/tags";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        if (response != null) {
            ArrayList<Object> arrayListTags = new ArrayList<>((Collection<?>) response.getBody());
            for (int j = 0; j < arrayListTags.size(); j++) {
                HashMap<String, Object> hashMapTags = (HashMap<String, Object>) arrayListTags.get(j);
                JSONObject jsonObjectTag = new JSONObject(hashMapTags);
                if (hashMapTags.get("release") != null) {
                    JSONObject jsonObjectTagRelease = jsonObjectTag.getJSONObject("release");
                    Long nameTag = jsonObjectTagRelease.getLong("tag_name");
                    String releaseNote = jsonObjectTagRelease.getString("description");
                    if (version.equals(nameTag))
                        return releaseNote;
                }
            }
        }
        return null;
    }


    public List<Tag> getTags() {
        System.out.println("----------------getTags------------------");
        ArrayList<Tag> tags = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
      //  String token = "glpat-Vie8uuParSMECxyrnhxw";
        String token = "glpat-B5opyUQDLEHK3SGC_w7A";
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        String url = "https://gitlab.com/api/v4/projects/36814198/repository/tags";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        if (response != null) {
            ArrayList<Object> arrayListTags = new ArrayList<>((Collection<?>) response.getBody());
            arrayListTags.stream().forEach(tagStream -> {
                HashMap<String, Object> hashMapTag = (HashMap<String, Object>) tagStream;
                System.out.println(hashMapTag);
                JSONObject jsonObjectTag = new JSONObject(hashMapTag);
                if (hashMapTag.get("release") != null) {
                    /*-------Get Tag--------*/
                    Tag tag = new Tag();
                    //Get Name Tag
                    Long nameTag = jsonObjectTag.getLong("name");
                    tag.setId(nameTag);
                    //Get Message
                    String message = jsonObjectTag.getString("message");
                    tag.setMessage(message);
                    //Get Release Note
                    JSONObject jsonObjectTagRelease = jsonObjectTag.getJSONObject("release");
                    String releaseNote = jsonObjectTagRelease.getString("description");
                    tag.setReleaseNote(releaseNote);
                    //Get Date Creation
                    JSONObject jsonObjectTagCommit = jsonObjectTag.getJSONObject("commit");
                    String dateCreation = jsonObjectTagCommit.getString("created_at");
                    // Convert String -> Date
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

                    Date date = null;
                    try {
                        date = inputFormat.parse(dateCreation);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String outputText = outputFormat.format(date);
                    tag.setDateCreation(outputText);

                    //Get Name Branche
                    deploiementRepository.findAll().forEach(deploiement -> {
                        String version = deploiement.getVersion();
                        if (version.equals(String.valueOf(nameTag))) {
                            deploiement.getBranche().setTags(tags);
                            tag.setBranche(deploiement.getBranche());
                            tag.setNameBranche(deploiement.getBranche().getName());
                            tag.setNameModule(deploiement.getBranche().getModule().getName());
                        }
                    });

                    tagRepository.save(tag);
                    tags.add(tag);
                    System.out.println(tags);
                }

            });

        }
        return tags;
    }


}

