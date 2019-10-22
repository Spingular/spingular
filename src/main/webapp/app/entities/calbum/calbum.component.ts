import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICalbum } from 'app/shared/model/calbum.model';
import { CalbumService } from './calbum.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from '../community/community.service';
import { AccountService } from 'app/core/auth/account.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-calbum',
  templateUrl: './calbum.component.html'
})
export class CalbumComponent implements OnInit, OnDestroy {
  currentAccount: any;
  calbums: ICalbum[];
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
  arrayCommmunities = [];
  appusers: IAppuser[];
  appuser: IAppuser;
  owner: any;
  isAdmin: boolean;

  constructor(
    protected calbumService: CalbumService,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected jhiAlertService: JhiAlertService,
    protected appuserService: AppuserService,
    protected communityService: CommunityService
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
    const query = {};
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.owner;
    }
    this.communityService.query(query).subscribe((res: HttpResponse<ICommunity[]>) => {
      this.communities = res.body;
      const query2 = {};
      if (this.communities != null) {
        this.arrayCommmunities = [];
        this.communities.forEach(community => {
          this.arrayCommmunities.push(community.id);
        });
        query2['communityId.in'] = this.arrayCommmunities;
      }
      this.calbumService
        .query(query2)
        .subscribe(
          (res2: HttpResponse<ICalbum[]>) => this.paginateCalbums(res2.body, res2.headers),
          (res2: HttpErrorResponse) => this.onError(res2.message)
        );
    });
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/calbum'], {
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
      '/calbum',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

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
          this.loadAll();
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.registerChangeInCalbums();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICalbum) {
    return item.id;
  }

  registerChangeInCalbums() {
    this.eventSubscriber = this.eventManager.subscribe('calbumListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCalbums(data: ICalbum[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.calbums = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
