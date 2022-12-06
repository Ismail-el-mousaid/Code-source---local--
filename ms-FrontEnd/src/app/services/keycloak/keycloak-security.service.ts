import {Injectable} from '@angular/core';
import {KeycloakInstance} from "keycloak-js";

declare var Keycloak: any;

@Injectable({
  providedIn: 'root'
})
export class KeycloakSecurityService {
  public kc: any;

  constructor() {
  }

  async init() {
    console.log("Security Initilialisation ...");
    //Démarrer une instance de keycloak
    this.kc = new Keycloak({

        url: "http://localhost:8080/auth",
        realm: "my-project-realm",
        clientId: "frontend-app"

    });
    await this.kc.init({      //await => j'attends la méthode init
      // onLoad:'login-required'     //authentification est obligatoire dès le démarrage
      onLoad: 'check-sso',  //authentifier utilisateur sans avoir besoin chaque fois de passer par un formulaire d'authentification
      promiseType: 'native',

     // checkLoginIframe: false

    });
    console.log(this.kc.token);
  }

}
