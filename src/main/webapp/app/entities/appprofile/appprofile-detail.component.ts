import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppprofile } from 'app/shared/model/appprofile.model';

@Component({
  selector: 'jhi-appprofile-detail',
  templateUrl: './appprofile-detail.component.html'
})
export class AppprofileDetailComponent implements OnInit {
  appprofile: IAppprofile;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appprofile }) => {
      this.appprofile = appprofile;
    });
  }

  previousState() {
    window.history.back();
  }
}
