import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../_model/user';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import RoleAuthority from '../_constants/roles.authorities';
import { AuthInterceptor } from '../_helpers/auth.interceptor';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  public get userId(): string {
    return this.currentUserSubject.value ? this.currentUserSubject.value._id : '';
  }

  signin(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/users/signin`, { email:username, password });
  }

  refreshToken(): Observable<any> {
    return this.http.post<any>(
      `${environment.apiUrl}/users/refresh_token`,
      {
        username: this.currentUserValue.email,
        password: this.currentUserValue.password
      }
    )
    .pipe(
        map(
          result => {
            if (result.data && result.data.user) {
              result.data.user.token = result.data.token;
              this.saveUserInSession(result.data.user);
              return result.data.user;
            }
            return result;
          }
        )
    );
  }

  saveUserInSession(user: User) {
    localStorage.setItem('currentUser', JSON.stringify(user));
    AuthInterceptor.user = user
    this.currentUserSubject.next(user);
  }

  recoverPassword(cpf: string): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/users/request_recovery/${cpf}`, null);
  }

  changePassword(token: string, obj: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/users/change_password/${token}`, obj);
  }

  signup(user: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/users/signup`, user);
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  isAdmin() {
    return this.checkPermission(RoleAuthority.ADMIN);
  }

  checkPermission(permissionName) {
    if ((this.currentUserValue && permissionName == RoleAuthority.ADMIN)) {
      return true;
    } else {
      return false;
    }
  }

  update(user: any): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/users/` + user._id, user);
  }

  readById(id: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/users/${id}`);
  }

}
