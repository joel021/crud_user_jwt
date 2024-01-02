import { Component, OnInit, Input } from "@angular/core";
import { AddressService } from "src/app/_service/address.service";
import { ToastrService } from 'ngx-toastr'

@Component({
    selector: "delete-address",
    templateUrl: "./delete.address.component.html",
})
export class DeleteAddress implements OnInit {

    @Input() id: string
    displayStyle = "none";

    constructor(
        private addressService: AddressService,
        private toastr: ToastrService
    ) {
        this.addressService = addressService
        this.toastr = toastr
    }

    ngOnInit() { }

    confirm() {
        this.displayStyle = "none";
        this.deleteAddress(this.id)
    }
    cancel() {
        this.displayStyle = "none";
    }
    openPopup() {
        this.displayStyle = "block";
    }

    deleteAddress(id: string) {

        /*
        this.addressService.deleteById(id).subscribe({
        next: resp => {
            window.location.reload()
        },
        error: resp => {
            this.cancel()
            this.toastr.success('Was not deleted. \n'+resp.message, 'Error');
        }
        })
        */
    }
}