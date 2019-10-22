import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiDataUtils, JhiAlertService } from 'ng-jhipster';

import { IPhoto } from 'app/shared/model/photo.model';
import { PhotoService } from './photo.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from '../community/community.service';
import { ICalbum } from 'app/shared/model/calbum.model';
import { CalbumService } from '../calbum/calbum.service';
import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from '../album/album.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

import { AccountService } from 'app/core/auth/account.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-photo',
  templateUrl: './photo.component.html'
})
export class PhotoComponent implements OnInit, OnDestroy {
  currentAccount: any;
  photos: IPhoto[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  communities: ICommunity[];
  calbums: ICalbum[];
  albums: IAlbum[];
  appusers: IAppuser[];
  appuser: IAppuser;
  owner: any;
  isAdmin: boolean;
  arrayAux = [];
  arrayIds = [];

  constructor(
    protected photoService: PhotoService,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected jhiAlertService: JhiAlertService,
    protected calbumService: CalbumService,
    protected communityService: CommunityService,
    protected albumService: AlbumService,
    protected appuserService: AppuserService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.photoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPhoto[]>) => this.paginatePhotos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/photo'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/photo',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  // ngOnInit() {
  //   this.loadAll();
  //   this.accountService.identity().subscribe(account => {
  //     this.currentAccount = account;
  //   });
  //   this.registerChangeInPhotos();
  // }
  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(
      account => {
        this.currentAccount = account;
        this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
        const query = {};
        if (this.currentAccount.id != null) {
          query['userId.equals'] = this.currentAccount.id;
        }
        this.appuserService.query(query).subscribe((res: HttpResponse<IAppuser[]>) => {
          this.owner = res.body[0].id;
          this.myUsersPhotos();
          // this.loggedUser = res.body[0];
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.registerChangeInPhotos();
  }

  myUsersPhotos() {
    const query = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.owner;
    }
    this.communityService.query(query).subscribe(
      (res: HttpResponse<ICommunity[]>) => {
        this.communities = res.body;
        this.userCommuntiesCalbums();
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private userCommuntiesCalbums() {
    const query = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    if (this.communities != null) {
      const arrayCommmunities = [];
      this.communities.forEach(community => {
        arrayCommmunities.push(community.id);
      });
      query['communityId.in'] = arrayCommmunities;
    }
    this.calbumService.query(query).subscribe(
      (res: HttpResponse<ICalbum[]>) => {
        this.calbums = res.body;
        this.userCommuntiesCalbumsPhotos();
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private userCommuntiesCalbumsPhotos() {
    const query = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    if (this.calbums != null) {
      const arrayCalbums = [];
      this.calbums.forEach(calbum => {
        arrayCalbums.push(calbum.id);
      });
      query['calbumId.in'] = arrayCalbums;
    }
    this.photoService.query(query).subscribe(
      (res: HttpResponse<IPhoto[]>) => {
        this.photos = res.body;
        this.usersAlbums();
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private usersAlbums() {
    const query = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.owner;
    }
    this.albumService.query(query).subscribe(
      (res: HttpResponse<IAlbum[]>) => {
        this.albums = res.body;
        this.usersAlbumsPhotos();
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private usersAlbumsPhotos() {
    const query = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    if (this.albums != null) {
      const arrayAlbums = [];
      this.albums.forEach(album => {
        arrayAlbums.push(album.id);
      });
      query['albumId.in'] = arrayAlbums;
    }
    this.photoService.query(query).subscribe(
      (res: HttpResponse<IPhoto[]>) => {
        //                        this.photos = this.photos.concat(res.body);
        this.photos = this.filterPhotos(this.photos.concat(res.body));
        this.paginatePhotos(this.photos, res.headers);
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private filterPhotos(photos) {
    this.arrayAux = [];
    this.arrayIds = [];
    photos.map(x => {
      if (this.arrayIds.length >= 1 && this.arrayIds.includes(x.id) === false) {
        this.arrayAux.push(x);
        this.arrayIds.push(x.id);
      } else if (this.arrayIds.length === 0) {
        this.arrayAux.push(x);
        this.arrayIds.push(x.id);
      }
    });
    return this.arrayAux;
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPhoto) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPhotos() {
    this.eventSubscriber = this.eventManager.subscribe('photoListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePhotos(data: IPhoto[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.photos = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
