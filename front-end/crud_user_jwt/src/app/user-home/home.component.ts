import { Component, OnInit, Inject, ElementRef } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_service/authentication.service';

@Component({
  selector: 'user-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class UserHome implements OnInit {

  loading = false;
  error = '';

  constructor(
    @Inject(DOCUMENT) public document,
    public elementRef: ElementRef,
    private authenticationService: AuthenticationService,
    private router: Router,
  ) {
    if (this.authenticationService.currentUserValue == null) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    
  }


}
