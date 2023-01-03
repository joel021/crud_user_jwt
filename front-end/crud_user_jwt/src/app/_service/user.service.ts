import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../_model/user';
import { FetchGenericService } from './fetch.generic.service';

@Injectable({ providedIn: 'root' })
export class UserService extends FetchGenericService {

  resourcePath = 'users';
  public currentUser: Observable<User>;

  constructor(public http: HttpClient) {
    super(http);
  }

  getUserById(userId: any): Observable<any> {
    return super.readById(`${this.resourcePath}/by_id`, userId);
  }

  update(user: any): Observable<any> {
    return super.update(this.resourcePath, user);
  }

  readUsers(page, filter): Observable<any> {
    return super.read(`${this.resourcePath}/${page}?search=${filter}`);
  }

  deleteById(userId: any): Observable<any> {
    return super.remove(this.resourcePath, userId);
  }
}
