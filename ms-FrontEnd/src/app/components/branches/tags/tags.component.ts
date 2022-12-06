import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';

import {MatTableDataSource} from "@angular/material/table";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort, Sort} from "@angular/material/sort";
import {ModuleService} from "../../../services/module.service";
import {FormControl, Validators} from "@angular/forms";


@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.css']
})
export class TagsComponent implements AfterViewInit {
  // colonnes que nous afficherons sur la table
  public displayedColumns = ['module', 'branche', 'nameTag', 'dateCreation', 'message', 'releaseNote'];
  // la source où nous obtiendrons les données
  public dataSource = new MatTableDataSource<any>();

  constructor(private service: ModuleService, private _liveAnnouncer: LiveAnnouncer) {
  }

  getTags() {
    this.service.getTagsInformation()
      .subscribe((res) => {
        console.log(res);
        this.dataSource.data = res['content'];
        // @ts-ignore
        console.log(this.dataSource.data.content)
      })
  }


  @ViewChild(MatPaginator) paginator: MatPaginator;

  @ViewChild(MatSort) sort: MatSort;

  ngAfterViewInit(): void {
    this.getTags();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

  }


  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }


  /*---Input----*/

  version = new FormControl('', [Validators.maxLength(10)]);

  getErrorMessage() {
    console.log("error " + this.version);
    return "Vous avez dépasser la longueur";
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


}

