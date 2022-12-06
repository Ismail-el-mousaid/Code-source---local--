import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class ModuleService {

  public host: string = environment.host;

  constructor(private http: HttpClient) {
  }

  public getModules() {
    return this.http.get(this.host + "/modules");
  }

  getEnvironnements() {
    return this.http.get(this.host + "/environnements");
  }

  getAllDeploiements(page: any, nameBranche: any, nameModule: any, nameEnv: any, size: any) {
    return this.http.get(this.host + "/deploiements?nameBranche=" + nameBranche + "&nameModule=" + nameModule + "&nameEnv=" + nameEnv + "&page=" + page + "&size=" + size);
  }

  getAllDeploiementsDateExist(page: any, nameBranche: any, nameModule: any, nameEnv: any, dateDep: any, size: any) {
    return this.http.get(this.host + "/deploiementsFindByDate?nameBranche=" + nameBranche + "&nameModule=" + nameModule + "&nameEnv=" + nameEnv + "&dateDeploiement=" + dateDep + "&page=" + page + "&size=" + size);
  }

  getAllDeploiementsByLast10Days() {
    return this.http.get(this.host + "/deploiementsDate");
  }

  getAllDeploiementsTriAsc(page: any, nameBranche: any, nameModule: any, nameEnv: any, size: any) {
    return this.http.get(this.host + "/deploiementsTriAsc?nameBranche=" + nameBranche + "&nameModule=" + nameModule + "&nameEnv=" + nameEnv + "&page=" + page + "&size=" + size);
  }

  getAllDeploiementsTriDesc(page: any, nameBranche: any, nameModule: any, nameEnv: any, size: any) {
    return this.http.get(this.host + "/deploiementsTriDesc?nameBranche=" + nameBranche + "&nameModule=" + nameModule + "&nameEnv=" + nameEnv + "&page=" + page + "&size=" + size);
  }

  getAllDeploiementsTriDesc2(nameModule: any, nameEnv: any) {
    return this.http.get(this.host + "/deploiementsTriDesc?nameModule=" + nameModule + "&nameEnv=" + nameEnv);
  }

  getBranches(currentModule: any) {
    return this.http.get(this.host + "/branches");
  }

  getDeploiements() {
    return this.http.get(this.host + "/deploiements");
  }


/*  getTags() {
    return this.http.get(this.host + "/tags");
  }  */

  getTagsInformation(): Observable<any> {
    return this.http.get<any>(this.host + "/tags");
  }

}
