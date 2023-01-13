import { Component, ElementRef, Inject, AfterViewInit } from '@angular/core';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'page-actions',
  templateUrl: './page-actions.component.html',
  styleUrls: ['./page-actions.component.css']
})
export class PageActionsComponent implements AfterViewInit {

  constructor(
    @Inject(DOCUMENT) public document,
    public elementRef: ElementRef,
  ) { }

  ngAfterViewInit() {
    const s = this.document.createElement('script');
    s.type = 'text/javascript';
    s.text = `
      $('[data-toggle="tooltip"]').tooltip();
      $(".preloader").fadeOut();
    `;
    this.elementRef.nativeElement.appendChild(s);
  }

}

