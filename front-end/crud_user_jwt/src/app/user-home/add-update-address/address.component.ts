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
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class CreateAddress extends PageActionsComponent implements OnInit {

  addressForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';
  user = null;
  addressId = null;

  constructor(
    @Inject(DOCUMENT) public document,
    public elementRef: ElementRef,
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private addressService: AddressService,
    private toastr: ToastrService
  ) {
    super(document, elementRef);
    this.user = this.authenticationService.currentUserValue;
  }

  ngOnInit() {
    this.addressId = this.route.snapshot.params['id'];

    this.addressForm = this.formBuilder.group({
      street: ['', Validators.required],
      district: ['', Validators.required],
      state: ['', Validators.required],
      country: ['', Validators.required],
      city: ['', Validators.required],
      number: ['', Validators.required]
    });

    if (this.addressId != null){
      this.fetchAddressById(this.addressId)
    }
    
  }

  get controls() { return this.addressForm.controls; }

  onSubmit() {

    this.submitted = true;

    if (this.addressForm.invalid) {
      return;
    }

    var address = {
      number: this.controls.number.value,
      street: this.controls.street.value,
      district: this.controls.district.value,
      city: this.controls.city.value,
      state: this.controls.state.value,
      country: this.controls.country.value,
      id: this.addressId
    }

    this.loading = true;
    if (this.addressId == null){
      this.createAddress(address);
    }else{
      this.putAddress(address);
    }
  }

  putAddress(address:any){
    this.addressService.put(
      address
    ).subscribe({
      next: respObject => {
        
        this.loading = false;
        
        this.router.navigate(['/home']).then(
          () => {
            this.toastr.success('Your address was updated successfully!', 'Success');
          }
        );
        
      },
      error: respError => {
        
        
        this.handleError(respError);
        
      }
    })
  }

  createAddress(address:any){
    this.addressService.createAddress(address)
    .subscribe({
        next: respObject => {
          this.loading = false;
          
          this.router.navigate(['/home']).then(
            () => {
              this.toastr.success('Your address was created successfully!', 'Success');
            }
          );
          
        },
        error: errorObject => {
          this.handleError(errorObject);
        }
      })
  }

  handleError(errorObject: any){
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

  fetchAddressById(id:string){
    this.addressService.getAddressById(id).subscribe({
      next: address => {
        this.addressForm.setValue({
          number: address.number,
          street: address.street,
          district: address.district,
          city: address.city,
          state: address.state,
          country: address.country
        })
      },
      error: errorObject => {
        this.toastr.error('Was not passible to retrieve the information about this address.', 'Error');
      }
    })
  }

}
