import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { FetchGenericService } from './fetch.generic.service';

@Injectable({ providedIn: 'root' })
export class CourseService extends FetchGenericService {

  resourcePath = 'users/course';

  constructor(public http: HttpClient) {
    super(http);
  }

  registerCourse(course: any): Observable<any> {
    return super.save(`${this.resourcePath}/`, course);
  }

  getCoursesByUserId(): Observable<any> {
    return super.read(`${this.resourcePath}/`);
  }

  searchCourseByName(name:string): Observable<any>{
    return super.readWithParams(`${this.resourcePath}/search`, { name: name })
  }

}
