import { Component, OnInit, Inject, ElementRef } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthenticationService } from 'src/app/_service/authentication.service';
import { CourseService as CourseService } from 'src/app/_service/course.service';

@Component({
  selector: 'register-course-page',
  templateUrl: './register-course.component.html',
  styleUrls: ['./register-course.component.css']
})
export class RegisterCoursePage implements OnInit {

  courseForm: FormGroup;
  loading = false;
  submitted = false;
  error = null;
  user = null;
  courseId = null;

  courseCreated=null

  constructor(
    @Inject(DOCUMENT) public document,
    public elementRef: ElementRef,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private courseService: CourseService,
  ) {
    this.user = this.authenticationService.currentUserValue;
  }

  ngOnInit() {
    this.courseId = this.route.snapshot.params['id'];

    this.courseForm = this.formBuilder.group({
      name: ['', Validators.required],
      year: ['', Validators.required]
    });

  }

  get controls() { return this.courseForm.controls; }

  onSubmit() {

    this.submitted = true;

    if (this.courseForm.invalid) {
      return;
    }

    var course = {
      name: this.controls.name.value,
      year: this.controls.year.value,
      id: this.courseId
    }

    this.loading = true;
    if (this.courseId == null){
      this.createcourse(course);
    }else{
      //this.putcourse(course);
    }
  }

  createcourse(course:any){
    this.courseService.registerCourse(course).subscribe({
      next: (resp) => {
        console.log(resp)
        this.error = null
        this.loading = false
        this.courseCreated = resp.name
      },
      error: (error) => {
        this.error = error.errors[0]
        this.loading = false
        this.courseCreated = null
      }
    })
  }


}
