import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from '../_service/authentication.service';
import { ToastrService } from 'ngx-toastr';
import { HTTP_STATUS_BAD_GATEWAY, HTTP_STATUS_UNAUTHORIZED } from '../_constants/http.constants';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(
    private authenticationService: AuthenticationService,
    private toastr: ToastrService
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      let error = err.message;
      if (err.status === HTTP_STATUS_UNAUTHORIZED) {
        this.handleUnauthorized();
        error = 'You have not a valid authentication. Please, perform the login process to get access.';
      } else if (err.status === HTTP_STATUS_BAD_GATEWAY) {
        error = 'Error when loading the page. Try again.';
      }
      return throwError(() => error);
    }));
  }

  handleUnauthorized() {
    this.authenticationService.logout();
    location.reload();
  }
}
