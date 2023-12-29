import { Component, OnInit, Inject, ElementRef } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/_service/authentication.service';
import { PageActionsComponent } from 'src/app/_components/page-actions/page-actions.component';
import { throwError } from 'rxjs';
import { HTTP_STATUS_UNAUTHORIZED } from 'src/app/_constants/http.constants';
import { User } from 'src/app/store/models/user.model';

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
      .pipe(catchError(respError => {
        
        if(respError.status == HTTP_STATUS_UNAUTHORIZED){
          this.error = "Your credentials is not correct";
        }else{
          this.error = respError.message;
        }
        
        this.loading = false;
        return throwError(() => respError.message);
      }))
      .subscribe(data => {
          this.loading = false;
          if (data.token != null) {
            const user = new User();
            user.email = data.email;
            user.token = data.token;
            user.authorities = [data.role];
            user._id = data.id;
            this.authenticationService.saveUserInSession(user);
            this.router.navigate(['/home'])
          } else {
            this.error = data.message;
          }
        },
        
      );
  }

  recoverPassword() {
    this.router.navigate(['/recover-password']);
  }

  signupUser() {
    this.router.navigate(['/signup']);
  }

}
