import { environment } from 'src/environments/environment';

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { FetchGenericService } from './fetch.generic.service';

import {EntityCollectionServiceBase, EntityCollectionServiceElementsFactory} from '@ngrx/data';
import { Address } from '../store/models/address.model';

@Injectable({ providedIn: 'root' })
export class AddressService extends EntityCollectionServiceBase<Address> {

  resourcePath = 'users/address';

  constructor(public serviceElementsFactory: EntityCollectionServiceElementsFactory) {
    super('Address', serviceElementsFactory);
  }

  /*
  createAddress(address: any): Observable<any> {
    return super.save(`${this.resourcePath}/`, address);
  }

  getAddressById(addressId: any): Observable<any> {
    return super.readById(`${this.resourcePath}`, addressId);
  }

  put(address: any): Observable<any> {
    return super.put(this.resourcePath, address);
  }

  deleteById(addressId: any): Observable<any> {
    return super.remove(this.resourcePath, addressId);
  }

  */
}
