import { Component, OnInit, Inject, ElementRef } from '@angular/core'
import { DOCUMENT } from '@angular/common'
import { Router } from '@angular/router'
import { ToastrService } from 'ngx-toastr'

import { AuthenticationService } from 'src/app/_service/authentication.service'
import { UserService } from '../../_service/user.service'
import { HTTP_STATUS_UNAUTHORIZED, HTTP_STATUS_FORBIDDEN } from 'src/app/_constants/http.constants'


@Component({
  selector: 'user-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class UserHome implements OnInit {

  loading = false
  error = null

  user = null
  pageSize = 5
  page = 1
  deleteAddressList = []
  
  displayModal = "none"

  constructor(
    @Inject(DOCUMENT) public document,
    public elementRef: ElementRef,
    private authenticationService: AuthenticationService,
    private router: Router,
    private userService: UserService,
    private toastr: ToastrService
  ) {
    if (this.authenticationService.currentUserValue == null) {
      this.router.navigate(['/'])
    }
  }

  ngOnInit() {
    this.fetchUser()
  }

  fetchUser(){
    
    this.loading = true
    this.userService.fetchAuthsUser()
      .subscribe({
        next: respObject => {
          this.loading = false
          this.user = respObject
        },
        error: errorObject => {
          this.loading = false

          this.authenticationService.logout()
          
          if (errorObject.status == HTTP_STATUS_UNAUTHORIZED || errorObject.status == HTTP_STATUS_FORBIDDEN){
            this.router.navigate(['/signin']).then(
              () => {
                this.toastr.error(errorObject.message, 'Error')
              }
            )
          }

          this.error = errorObject.message
        }
      })
  }

  logout(){
    this.authenticationService.logout()
  }

  addAddress(){
    this.router.navigate(['/users/address'])
  }

  handlePageChange(event:any) {
    this.page = event
  }
  
}
