import { Component } from '@angular/core'
import { Observable } from 'rxjs'
import { AddressService } from 'src/app/_service/address.service'
import { Address } from 'src/app/store/models/address.model'

@Component({
  selector: 'app-list-addresses',
  templateUrl: './list-addresses.component.html',
  styleUrls: ['./list-addresses.component.css']
})
export class ListAddressesComponent {

  addresses$: Observable<Address[]>

  constructor(private addressService: AddressService) {
    this.addresses$ = addressService.entities$
  }

  ngOnInit() {
    this.getPosts()
  }

  getPosts() {
    this.addressService.getAll()
  }

}
