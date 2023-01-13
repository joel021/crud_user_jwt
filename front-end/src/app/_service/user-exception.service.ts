import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FetchGenericService } from './fetch.generic.service';

@Injectable()
export class UserExceptionService extends FetchGenericService {

    resourcePath = 'exceptions';

    constructor(public http: HttpClient) {
        super(http);
    }

    readByType(type: string): Observable<any> {
        return super.readById(this.resourcePath, type);
    }

    readAll(): Observable<any> {
        return super.read(this.resourcePath);
    }

}
