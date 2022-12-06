import {Component, OnInit} from '@angular/core';
import {KeycloakSecurityService} from "./services/keycloak/keycloak-security.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'ms-FrontEnd';

  public currentHome: any;
  public currentDeploiement: any;

  constructor(public securityService: KeycloakSecurityService, private router: Router) {

  }

  public href: string = "";

  ngOnInit(): void {
    this.href = this.router.url;
    this.currentHome = true;

  }

  onLogout() {
    this.securityService.kc.logout();
  }

  onLogin() {
    this.securityService.kc.login();
  }

  onRedirectToProfile() {
    this.securityService.kc.accountManagement();
  }

  onChangePassword() {
    // this.securityService.kc.accountManagement("/password");
    this.securityService.kc.login({
      action: "UPDATE_PASSWORD",
    });
  }

  async isRoleEmploye() {
    if (!this.securityService.kc.authenticated) {
      await this.securityService.kc.login();
    }
    return this.securityService.kc.hasRealmRole('EMPLOYE');
  }


  isRouterActive(path: string) {
    if (path == 'home') {
      this.currentHome = true;
      this.currentDeploiement = false;
    }
    if (path == 'dep') {
      this.currentDeploiement = true;
      this.currentHome = false;
    }

  }

}
