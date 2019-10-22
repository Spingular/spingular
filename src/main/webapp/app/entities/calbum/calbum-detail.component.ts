import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
// import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ICalbum } from 'app/shared/model/calbum.model';
import { IPhoto } from 'app/shared/model/photo.model';
import { PhotoService } from '../photo/photo.service';

@Component({
  selector: 'jhi-calbum-detail',
  templateUrl: './calbum-detail.component.html'
})
export class CalbumDetailComponent implements OnInit {
  calbum: ICalbum;

  photos: IPhoto[];

  constructor(protected activatedRoute: ActivatedRoute, protected photoService: PhotoService, protected jhiAlertService: JhiAlertService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ calbum }) => {
      this.calbum = calbum;
      this.usersCalbumsPhotos();
    });
  }

  previousState() {
    window.history.back();
  }

  protected usersCalbumsPhotos() {
    const query = {};
    if (this.calbum != null) {
      const arrayAlbums = [];
      query['calbumId.in'] = this.calbum.id;
    }
    this.photoService.query(query).subscribe(
      (res: HttpResponse<IPhoto[]>) => {
        this.photos = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackId(index: number, item: IPhoto) {
    return item.id;
  }
}
