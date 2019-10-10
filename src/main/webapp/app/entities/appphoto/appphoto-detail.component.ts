import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAppphoto } from 'app/shared/model/appphoto.model';

@Component({
  selector: 'jhi-appphoto-detail',
  templateUrl: './appphoto-detail.component.html'
})
export class AppphotoDetailComponent implements OnInit {
  appphoto: IAppphoto;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appphoto }) => {
      this.appphoto = appphoto;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
