import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from './album.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-album',
  templateUrl: './album.component.html'
})
export class AlbumComponent implements OnInit, OnDestroy {
  currentAccount: any;
  albums: IAlbum[];
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

  appusers: IAppuser[];
  appuser: IAppuser;
  owner: any;
  isAdmin: boolean;

  constructor(
    protected albumService: AlbumService,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
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

  // loadAll() {
  //   this.albumService
  //     .query({
  //       page: this.page - 1,
  //       size: this.itemsPerPage,
  //       sort: this.sort()
  //     })
  //     .subscribe((res: HttpResponse<IAlbum[]>) => this.paginateAlbums(res.body, res.headers));
  // }

  loadAll() {
    const query = {};
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.owner;
    }
    this.albumService.query(query).subscribe((res: HttpResponse<IAlbum[]>) => this.paginateAlbums(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/album'], {
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
      '/album',
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
  //   this.registerChangeInAlbums();
  // }

  ngOnInit() {
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
          // this.loggedUser = res.body[0];
          this.loadAll();
          res.body, res.headers;
        });
      }
      // (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.registerChangeInAlbums();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAlbum) {
    return item.id;
  }

  registerChangeInAlbums() {
    this.eventSubscriber = this.eventManager.subscribe('albumListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAlbums(data: IAlbum[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.albums = data;
  }
}
