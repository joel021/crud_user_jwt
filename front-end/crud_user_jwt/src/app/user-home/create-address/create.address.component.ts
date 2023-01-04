import { Component, OnInit, Inject, ElementRef } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthenticationService } from 'src/app/_service/authentication.service';
import { PageActionsComponent } from 'src/app/_components/page-actions/page-actions.component';
import { AddressService } from 'src/app/_service/address.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'create-address',
  templateUrl: './create.address.component.html',
  styleUrls: ['./create.address.component.css']
})
export class CreateAddress extends PageActionsComponent implements OnInit {

  addressForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';
  user = null;

  constructor(
    @Inject(DOCUMENT) public document,
    public elementRef: ElementRef,
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private addressService: AddressService,
    private toastr: ToastrService
  ) {
    super(document, elementRef);
    this.user = this.authenticationService.currentUserValue;
  }

  ngOnInit() {
    this.addressForm = this.formBuilder.group({
      street: ['', Validators.required],
      district: ['', Validators.required],
      state: ['', Validators.required],
      country: ['', Validators.required],
      city: ['', Validators.required],
      number: ['', Validators.required]
    });
  }

  get controls() { return this.addressForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.addressForm.invalid) {
      return;
    }

    this.loading = true;
    this.addressService.createAddress(
      {
        number: this.controls.number.value,
        street: this.controls.street.value,
        district: this.controls.district.value,
        city: this.controls.city.value,
        state: this.controls.state.value,
        country: this.controls.country.value
      }).subscribe({
        next: respObject => {
          this.loading = false;
          
          this.router.navigate(['/home']).then(
            () => {
              this.toastr.success('Your address was created successfully!', 'Success');
            }
          );
          
        },
        error: errorObject => {
          console.log("Errorrrr:")
          console.log(errorObject)
          
          if (errorObject.errors != null){
            
            this.router.navigate(['/home']).then( 
              () => {
                for(var i = 0; i < errorObject.errors.length; i++){
                  this.toastr.error(errorObject.errors[i], 'Error');
                }
              }
            );
          } else if(errorObject.message != null){
            this.router.navigate(['/home']).then( 
              () => { this.toastr.error(errorObject.message, 'Error');}
            );
          }else{
            this.router.navigate(['/home']).then( 
              () => { this.toastr.error('Unfortunately your request was not performed correctly.', 'Error'); }
            );
          }
          
        }
      })
  }


}
