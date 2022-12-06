import { Component, OnInit } from '@angular/core';
import {ModuleService} from "../../../services/module.service";

@Component({
  selector: 'app-live-deploiements',
  templateUrl: './live-deploiements.component.html',
  styleUrls: ['./live-deploiements.component.css']
})
export class LiveDeploiementsComponent implements OnInit {

  public deploiementsLive: any;
  public listDatesDeploiements: any = [];

  public environnementsList: any = ['INT', 'ITT', 'ACC', 'Pre-prod', 'Prod']

  constructor(private service: ModuleService) { }

  ngOnInit(): void {
    this.getModules();
    this.getEnvironnements();
    setTimeout(() => {
      this.getDeploiementsLive();
    }, 700)
  }

  public modules: any;

  public getModules(){
    return this.service.getModules()
        .subscribe(data =>{
          this.modules=data;
        }, error => {
          console.log(error);
        }
      )
  }

  public environnements: any;

  public getEnvironnements(){
    return this.service.getEnvironnements()
      .subscribe(data =>{
          this.environnements=data;
        }, error => {
          console.log(error);
        }
      )
  }

  public tab: any = [];

  public getDeploiementsLive(){
    for (let module of this.modules) {
      for (let env of this.environnements) {
        this.service.getAllDeploiementsTriDesc2(module.name, env.name)
          .subscribe(data => {
            this.deploiementsLive = data;
            console.log(" "+module.name+" "+env.name+" "+this.deploiementsLive.content[0].dateDeploiement);
       /*     for (const depl of this.deploiementsLive.content) {
              this.listDatesDeploiements.push(depl.dateDeploiement);
            }  */
            this.tab[module.name+env.name+"dateDeploiement"]=this.deploiementsLive.content[0].dateDeploiement;
            this.tab[module.name+env.name+"nameBranche"]=this.deploiementsLive.content[0].branche.name;
            this.tab[module.name+env.name+"version"]=this.deploiementsLive.content[0].version;
          }, error => {
              console.log(error);
          })
      }
    }
      console.log("modules" +this.modules);

  /*  return this.service.getDeploiements()
      .subscribe(data =>{
        this.deploiementsLive=data;
        console.log(this.deploiementsLive);
        for (const depl of this.deploiementsLive.content) {
          this.listDatesDeploiements.push(depl.dateDeploiement);
        }
        var sortedArray: Date[] = this.listDatesDeploiements.sort((n1:any,n2:any) => n1 - n2);
        console.log("sorted : "+sortedArray);
        console.log("list : "+this.listDatesDeploiements);
      }, error => {
        console.log(error);
      })  */
  }

}
