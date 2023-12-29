import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../store/models/user.model';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    static user: User = null;

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const user = AuthInterceptor.getUser();
        req.headers.append('Cache-Control', 'no-cache')

        if (user) {
            req = this.addToken(req, user.token);
        }

        return next.handle(req).pipe(catchError(error => {
            return throwError(() => error);
        }));

    }

    static getUser() {
        if (AuthInterceptor.user == null)
            AuthInterceptor.user = <User> JSON.parse(localStorage.getItem('currentUser'));
        return AuthInterceptor.user;
    }

    private addToken(request: HttpRequest<any>, token: string) {
        return request.clone({
            setHeaders: {
                'x-access-token': token,
                'Cache-Control':  'no-cache, no-store, must-revalidate, post-check=0, pre-check=0',
                'Pragma': 'no-cache',
                'Expires': '0'
            }
        });
    }
}
