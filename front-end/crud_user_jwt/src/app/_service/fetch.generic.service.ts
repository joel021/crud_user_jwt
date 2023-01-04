import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export class FetchGenericService {

  constructor(public http: HttpClient) { }

  save(resource: string, object: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/${resource}`, object);
  }

  put(resource: string, object: any): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/${resource}/${object.id}`, object);
  }

  update(resource: string, object: any): Observable<any> {
    return this.http.patch<any>(`${environment.apiUrl}/${resource}/${object.id}`, object);
  }

  updateWithoutId(resource: string, object: any): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/${resource}`, object);
  }

  customUpdate(resource: string, object: any): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/${resource}`, object);
  }

  remove(resource: string, id: string): Observable<any> {
    return this.http.delete<any>(`${environment.apiUrl}/${resource}/${id}`);
  }

  read(resource: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/${resource}`);
  }

  readById(resource: string, id: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/${resource}/${id}`);
  }

}
