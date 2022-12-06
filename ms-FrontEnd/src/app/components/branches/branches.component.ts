import {Component, OnInit} from '@angular/core';
import {ModuleService} from "../../services/module.service";
import {KeycloakSecurityService} from "../../services/keycloak/keycloak-security.service";

@Component({
  selector: 'app-branches',
  templateUrl: './branches.component.html',
  styleUrls: ['./branches.component.css']
})
export class BranchesComponent implements OnInit {

  constructor(private service: ModuleService, public securityService: KeycloakSecurityService) {
  }

  public deploiements: any;
  public nameBranche: string = "";
  public nameModule: string = "";
  public nameEnv: string = "";
  //Pagination
  public currentPage = 0;
  public pages: Array<number> | undefined;
  public totalPages: any;
  public currentSize = 5;

  public currentDate = new Date();

  public etatBtn: any;

  ngOnInit(): void {
    this.getDeploiements(this.currentPage, this.nameBranche, this.nameModule, this.nameEnv, this.currentSize);

  }

  setPageNext() {
    if (this.currentPage == this.totalPages - 1) {
      this.currentPage = this.currentPage;
    } else {
      this.currentPage += 1;
      this.getDeploiements(this.currentPage, this.nameBranche, this.nameModule, this.nameEnv, this.currentSize);
    }
  }

  setPagePrevious() {
    if (this.currentPage == 0) {
      this.currentPage = 0;
    } else {
      this.currentPage -= 1;
      this.getDeploiements(this.currentPage, this.nameBranche, this.nameModule, this.nameEnv, this.currentSize);
    }
  }

  public tri: string = "";

  getDeploiements(i: any, keywordBranche: any, keywordModule: any, keywordEnv: any, size: any) {
    this.currentSize = size;
    this.currentPage = i;

    if (this.nameModule != keywordModule || this.nameBranche != keywordBranche || this.nameEnv != keywordEnv) {
      i = 0;
      this.currentPage = i;
    }

    this.nameEnv = keywordEnv;
    this.nameBranche = keywordBranche;
    this.nameModule = keywordModule;

    if (this.currentDateDeploiement) {
      this.service.getAllDeploiementsDateExist(this.currentPage, this.nameBranche, this.nameModule, this.nameEnv, this.currentDateDeploiement, this.currentSize)
        .subscribe(data => {
          this.deploiements = data;
          console.log(data)
          // @ts-ignore
          this.totalPages = data['totalPages'];
          // @ts-ignore
          this.pages = new Array<number>(this.totalPages);
        }, error => {
          console.log(error);
        })
    } else if (this.tri == "") {
      this.service.getAllDeploiements(i, keywordBranche, keywordModule, keywordEnv, size)
        .subscribe(data => {
          this.deploiements = data;
          // @ts-ignore
          this.totalPages = data['totalPages'];
          // @ts-ignore
          this.pages = new Array<number>(this.totalPages);
        }, error => {
          console.log(error);
        })
    } else if (this.tri == "ascendant") {
      this.service.getAllDeploiementsTriAsc(this.currentPage, this.nameBranche, this.nameModule, this.nameEnv, this.currentSize)
        .subscribe(data => {
          this.deploiements = data;
          // @ts-ignore
          this.totalPages = data['totalPages'];
          // @ts-ignore
          this.pages = new Array<number>(this.totalPages);
        }, error => {
          console.log(error);
        })
    } else if (this.tri == "descendant") {
      this.service.getAllDeploiementsTriDesc(this.currentPage, this.nameBranche, this.nameModule, this.nameEnv, this.currentSize)
        .subscribe(data => {
          this.deploiements = data;
          // @ts-ignore
          this.totalPages = data['totalPages'];
          // @ts-ignore
          this.pages = new Array<number>(this.totalPages);
        }, error => {
          console.log(error);
        })
    }

  }

  /*--------Tri Ascendant---------*/
  getDeploiementsTriAsc() {
    console.log("tri ");
    this.currentPage = 0;
    this.tri = "ascendant";
    this.getDeploiements(this.currentPage, this.nameBranche, this.nameModule, this.nameEnv, this.currentSize);
  }

  /*-------Tri Descendant--------*/
  getDeploiementsTriDesc() {
    this.currentPage = 0;
    this.tri = "descendant";
    this.getDeploiements(this.currentPage, this.nameBranche, this.nameModule, this.nameEnv, this.currentSize);
  }

  /*------Bootstrap Env------*/
  getClassBtsrpByEnv(nameEnv: any) {
    if (nameEnv == 'ITT') {
      return 'badge-info';
    }
    if (nameEnv == 'INT') {
      return 'badge-secondary';
    }
    if (nameEnv == 'ACC') {
      return 'badge-warning';
    }
    if (nameEnv == 'Pre-prod') {
      return 'badge-primary';
    }
    if (nameEnv == 'Prod') {
      return 'badge-success';
    }

    return 'badge-pill';
  }


  public visible = false;
  public releaseNote: any;

  afficherAlertReleaseNote(release: any) {
    this.releaseNote = release;
    return this.visible = true;
  }

  hiddenAlertReleaseNote() {
    return this.visible = false;
  }

  isRoleEmploye() {
    return this.securityService.kc.hasRealmRole('EMPLOYE');
  }

  public keywordDate: any;
  public currentDateDeploiement: any;

  getDeploiementsDateExist(dateValue: string) {
    this.currentDateDeploiement = dateValue;
    this.currentPage = 0;
    this.getDeploiements(this.currentPage, this.nameBranche, this.nameModule, this.nameEnv, this.currentSize);
  }

  public etat = 'Tableau';

  activeCard(value: any) {
    return this.etat = value;
  }



}

export interface IHush {
  [details: number]: any;
}
