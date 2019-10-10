import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppuser } from 'app/shared/model/appuser.model';

@Component({
  selector: 'jhi-appuser-detail',
  templateUrl: './appuser-detail.component.html'
})
export class AppuserDetailComponent implements OnInit {
  appuser: IAppuser;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appuser }) => {
      this.appuser = appuser;
    });
  }

  previousState() {
    window.history.back();
  }
}
