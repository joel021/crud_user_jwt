import { Component, OnInit, Inject, ElementRef } from '@angular/core'
import { DOCUMENT } from '@angular/common'
import { Router } from '@angular/router'

import { AuthenticationService } from 'src/app/_service/authentication.service'
import { CourseService } from 'src/app/_service/course.service'

import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'courses-page',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesPage implements OnInit {

  loading = false
  error = null

  user = null
  pageSize = 5
  page = 1
  
  displayModal = "none"
  message = ""
  courses = null

  searchForm: FormGroup

  submitted = false

  courseMessage = null

  constructor(
    @Inject(DOCUMENT) public document,
    public elementRef: ElementRef,
    private authenticationService: AuthenticationService,
    private router: Router,
    private courseService: CourseService,
    private formBuilder: FormBuilder,
  ) {
    if (this.authenticationService.currentUserValue == null) {
      this.router.navigate(['/'])
    }
  }

  ngOnInit() {

    this.searchForm = this.formBuilder.group({
      name: ['', Validators.required]
    });

    this.getCoursesByUserId()
  }

  getCoursesByUserId(){
    this.loading = true

    this.courseService.getCoursesByUserId().subscribe({
      next: (resp) => {
          console.log(resp)
          
          if(resp == null || resp.length == 0){
            this.courses = null;
            this.courseMessage = "Não há item registrado"
          }else{
            this.courses = resp;
          }

          this.loading = false
      },
      error: (err) => {
          console.log(err)
          this.loading = false
      }
    })
  }


  updateCourses(){

    if (this.searchForm.invalid) {
      this.getCoursesByUserId()
    }else{
      this.searchCourse()
    }

  }

  searchCourse(){

    this.courseService.searchCourseByName(this.controls.name.value).subscribe({
      next: (resp) => {
        console.log(resp)
        if(resp == null || resp.length == 0){
          this.courses = null;
          this.courseMessage = "Não há item registrado para esse filtro."
        }else{
          this.courses = resp;
        }

        this.loading = false
      },
      error: (error) => {
        console.log(error)
      }
    })
  }

  logout(){
    this.authenticationService.logout()
  }

  handlePageChange(event:any) {
    this.page = event
  }
  
  registerCourse(){
    this.router.navigate(['users/register-course'])
  }

  get controls() { return this.searchForm.controls; }
}
