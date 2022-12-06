import { Component, OnInit } from '@angular/core';
import {DatePipe} from "@angular/common";
import {IHush} from "../branches.component";
import {ModuleService} from "../../../services/module.service";

@Component({
  selector: 'app-graphes',
  templateUrl: './graphes.component.html',
  styleUrls: ['./graphes.component.css']
})
export class GraphesComponent implements OnInit {

  constructor(private service: ModuleService) { }

  ngOnInit(): void {

    //Graphe1
    this.getDeploiementsByDateAndEnv();
    //Graphe2
    this.getModules();
    setTimeout(() => {
      this.getModulesAndNbrBranches();
    }, 300)

  }


  //------------------Chart-------------------------//


  /*------------Graphe 1--------------*/

  public listEnv = ["INT", "ITT", "ACC", "Pre-prod", "Prod"]
  public listDate = [
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate() - 9), 'yyyy-MM-dd'),
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate() - 8), 'yyyy-MM-dd'),
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate() - 7), 'yyyy-MM-dd'),
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate() - 6), 'yyyy-MM-dd'),
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate() - 5), 'yyyy-MM-dd'),
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate() - 4), 'yyyy-MM-dd'),
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate() - 3), 'yyyy-MM-dd'),
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate() - 2), 'yyyy-MM-dd'),
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate() - 1), 'yyyy-MM-dd'),
    new DatePipe('en-US').transform(new Date().setDate(new Date().getDate()), 'yyyy-MM-dd')
  ]

  public chartData: any;

  public initLineChart(data: any) {
    this.chartData = {
      title: 'Nbrs des branches déployées pendant les 10 dernières jours dans les 5 environnements de déploiements',
      type: 'LineChart',
      data:
      data
      ,
      columnNames: ["Day", "INT", "ITT", "ACC", "Pre-Prod", "Prod"],
      options: {
        hAxis: {
          title: 'Les 10 dernières jours'
        },
        vAxis: {
          title: 'Nbr des branches déployés'
        },
      },
      width: 1000,
      height: 400
    };

  }

  public deploiementsGraphe1: any;
  public tab: any = [];
  public dataTabLineChart: any = [];

  public getDeploiementsByDateAndEnv() {
    for (let l = 0; l < this.listDate.length; l++) {
      for (let o = 0; o < this.listEnv.length; o++) {
        this.tab[this.listEnv[o] + this.listDate[l]] = 0;
        console.log("remplire " + this.listEnv[o] + this.listDate[l]);
      }
    }
    this.service.getAllDeploiementsByLast10Days()
      .subscribe(data => {
        this.deploiementsGraphe1 = data;
        console.log("div " + 8 / 2);
        for (let d of this.deploiementsGraphe1.content) {
          for (let l = 0; l < this.listDate.length; l++) {
            if (new DatePipe('en-US').transform(d.dateDeploiement, 'yyyy-MM-dd') == this.listDate[l]) {
              this.tab[d.environnement.name + this.listDate[l]]++;
            }
          }
        }
        for (let l = 0; l < this.listDate.length; l++) {
          let p = [new DatePipe('en-US').transform(this.listDate[l], 'MM/dd'), this.tab['INT' + this.listDate[l]], this.tab['ITT' + this.listDate[l]], this.tab['ACC' + this.listDate[l]], this.tab['Pre-prod' + this.listDate[l]], this.tab['Prod' + this.listDate[l]]];
          this.dataTabLineChart.push(p);
        }
        this.initLineChart(this.dataTabLineChart);
      })
  }

  /*-------Graphe 2---------*/

  public modules: any;

  public getModules() {
    this.service.getModules()
      .subscribe(data => {
        this.modules = data;
      })
  }

  public listModules: IHush = {};
  public deploiementsGraphe2: any;
  public nbrBranchs = 0;
  public dataBarChart: any;
  public dataTabBarChart: any = [];
  public chartDataBar: any;

  public getModulesAndNbrBranches() {
    this.service.getDeploiements()
      .subscribe(data => {
        this.deploiementsGraphe2 = data;
        for (let m of this.modules) {
          for (let dep of this.deploiementsGraphe2.content) {
            if (dep.branche.module.name == m.name)
              this.nbrBranchs++;
          }
          this.listModules[m.name] = this.nbrBranchs;
          console.log("tst " + this.listModules[m.name]);
          this.dataBarChart =
            [m.name, this.listModules[m.name]];
          this.dataTabBarChart.push(this.dataBarChart);
          this.nbrBranchs = 0;
          console.log("Module " + m.name + "=>" + this.listModules[m.name]);
        }
      });

    setTimeout(() => {
      this.initBarChart(this.dataTabBarChart);
    }, 600);

  }

  public initBarChart(data: any) {
    this.chartDataBar = {
      title: 'Nombres des Branches déployées par Module',
      type: 'BarChart',
      data:
      data
      ,
      columnNames: ['Nombres', 'Nbr des branches déployées'],
      options: {},
      width: 1000,
      height: 400
    };

  }



}
