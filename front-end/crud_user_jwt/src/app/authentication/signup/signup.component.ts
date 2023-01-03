import { Component, OnInit, Inject, ElementRef } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';


import { AuthenticationService } from 'src/app/_service/authentication.service';
import { PageActionsComponent } from 'src/app/_components/page-actions/page-actions.component';
import { CustomValidators } from 'src/app/_helpers/validators';

@Component({
  selector: 'sinup-component',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class Signup extends PageActionsComponent implements OnInit {

  registrationForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';

  constructor(
    @Inject(DOCUMENT) public document,
    public elementRef: ElementRef,
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private toastr: ToastrService
  ) {
    super(document, elementRef);
  }

  ngOnInit() {
    this.registrationForm = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
        passwordConfirmation: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      },
      {
        validators: [
          CustomValidators.passwordMatch
        ]
      }
    );
  }

  get controls() { return this.registrationForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.registrationForm.invalid) {
      return;
    }

    this.loading = true;
    const user = {
      email: this.controls.email.value,
      password: this.controls.password.value,
    };
    this.authenticationService.signup(user)
      .pipe(catchError(error => {
        this.toastr.error(error, 'Erro');
        return throwError(() => error);
      }))
      .subscribe(
        data => {
          this.loading = false;
          this.router.navigate(['/']).then(
            () => {
              this.toastr.success('Your account was created successfully!', 'Success');
            }
          );
    })
  }

}
