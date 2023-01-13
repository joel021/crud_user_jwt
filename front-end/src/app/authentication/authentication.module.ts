import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { Signup } from './signup/signup.component';
import { Signin } from './signin/signin.component';
import { PageActionsComponent } from '../_components/page-actions/page-actions.component';

@NgModule({
  declarations: [
    Signup,
    Signin,
    PageActionsComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule
  ]
})
export class AuthenticationModule { }
