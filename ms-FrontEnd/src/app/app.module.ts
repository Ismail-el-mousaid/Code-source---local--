import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {KeycloakSecurityService} from "./services/keycloak/keycloak-security.service";
import {RequestInterceptorService} from "./services/keycloak/request-interceptor.service";
import {SlickCarouselModule} from "ngx-slick-carousel";
import {BranchesComponent} from './components/branches/branches.component';
import {HomeComponent} from './components/home/home.component';
import {GoogleChartsModule} from "angular-google-charts";
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {AppMaterialModule} from "./app.material-module";
import {TagsComponent} from './components/branches/tags/tags.component';
import { GraphesComponent } from './components/branches/graphes/graphes.component';
import { LiveDeploiementsComponent } from './components/branches/live-deploiements/live-deploiements.component';

//Initialiser le service keycloak avant même de charger l'application en exploitant 'les providers'
function kcFactory(kcSecurity: KeycloakSecurityService) {
  return () => kcSecurity.init();
}


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    BranchesComponent,
    HomeComponent,
    TagsComponent,
    GraphesComponent,
    LiveDeploiementsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    SlickCarouselModule,
    GoogleChartsModule,
    NoopAnimationsModule,
    AppMaterialModule,
    ReactiveFormsModule
  ],
  providers: [
    {provide: APP_INITIALIZER, deps: [KeycloakSecurityService], useFactory: kcFactory, multi: true},  //Permet de démarrer avant que l'application démarre
    {provide: HTTP_INTERCEPTORS, useClass: RequestInterceptorService, multi: true}  //Permet de démarrer Intercepteur à chaque fois qu'il y'a une requete existe

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
