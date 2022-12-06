import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {BranchesComponent} from "./components/branches/branches.component";
import {HomeComponent} from "./components/home/home.component";

const routes: Routes = [
  {
    path: "login", component: LoginComponent
  },
  {
    path: "branches", component: BranchesComponent
  },
  {
    path: "", component: HomeComponent
  }
  /*{
    path: '**', redirectTo: ''
  }*/
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
