import { Component, OnInit, Inject, ElementRef } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/_service/authentication.service';
import { PageActionsComponent } from 'src/app/_components/page-actions/page-actions.component';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class Signin extends PageActionsComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';

  constructor(
    @Inject(DOCUMENT) public document,
    public elementRef: ElementRef,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    super(document, elementRef);
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  get controls() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authenticationService.signin(this.controls.username.value, this.controls.password.value)
      .pipe(catchError(error => {
        this.error = error;
        this.loading = false;
        return throwError(() => error);
      }))
      .subscribe(
        data => {
          this.loading = false;
          if (data.token != null) {
            const loggedUser = data.data.user;
            loggedUser.token = data.data.token;
            this.authenticationService.saveUserInSession(loggedUser);
            this.router.navigate([this.returnUrl]);
          } else {
            this.error = data.message;
          }
        }
      );
  }

  recoverPassword() {
    this.router.navigate(['/recover-password']);
  }

  registerUser() {
    this.router.navigate(['/signup']);
  }

}
