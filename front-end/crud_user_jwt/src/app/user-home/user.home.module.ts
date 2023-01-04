import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';
import { UserHome } from './home/home.component';
import { CreateAddress } from './address/create.address.component';

@NgModule({
  declarations: [
    UserHome,
    CreateAddress
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ]
})
export class UserHomeModule { }
