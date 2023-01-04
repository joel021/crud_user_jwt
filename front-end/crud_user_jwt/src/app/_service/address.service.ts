import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { FetchGenericService } from './fetch.generic.service';

@Injectable({ providedIn: 'root' })
export class AddressService extends FetchGenericService {

  resourcePath = 'users/address';

  constructor(public http: HttpClient) {
    super(http);
  }

  createAddress(address: any): Observable<any> {
    return super.save(`${this.resourcePath}/`, address);
  }

  getAddressById(addressId: any): Observable<any> {
    return super.readById(`${this.resourcePath}/by_id`, addressId);
  }

  put(address: any): Observable<any> {
    return super.put(this.resourcePath, address);
  }

  deleteById(addressId: any): Observable<any> {
    return super.remove(this.resourcePath, addressId);
  }
}
