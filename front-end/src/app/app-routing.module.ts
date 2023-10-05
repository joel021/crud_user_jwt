import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Signin } from './authentication/signin/signin.component';
import { Signup } from './authentication/signup/signup.component'
import { CreateAddress } from './user-home/add-update-address/address.component';
import { RegisterCoursePage } from './user-home/register-course/register-course.component'
import { CoursesPage } from './user-home/courses/courses.component';
import { UserHome } from './user-home/home/home.component';

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
    path: "users/address",
    component: CreateAddress
  },
  {
    path: "users/address/:id",
    component: CreateAddress
  },
  {
    path: "users/courses",
    component: CoursesPage
  },
  {
    path: "users/register-course",
    component: RegisterCoursePage
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
