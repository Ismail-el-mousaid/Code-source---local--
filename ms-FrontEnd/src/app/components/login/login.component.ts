import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }


  private etat = true;
  public typePass = "password";

  maskUnmsakPass() {
    this.etat = !this.etat;
    this.typePass = this.etat == true ? "password" : "text";
  }


}
