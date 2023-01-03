import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Signin } from './authentication/signin/signin.component';
import { Signup } from './authentication/signup/signup.component'
import { UserHome } from './user-home/home.component';

const routes: Routes = [
  {
    path: "signup",
    component: Signup
  },
  {
    path: "signin",
    component: Signin
  },
  {
    path: "home",
    component: UserHome
  },
  {
    path: "**", redirectTo: "signin"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: "reload" })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
