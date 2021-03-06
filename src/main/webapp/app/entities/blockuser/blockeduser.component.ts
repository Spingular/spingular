import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IBlockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from './blockuser.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

import { AccountService } from 'app/core/auth/account.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-blockuser',
  templateUrl: './blockeduser.component.html'
})
export class BlockeduserComponent implements OnInit, OnDestroy {
  currentAccount: any;
  blockusers: IBlockuser[];
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

  nameParamBlockUser: any;
  valueParamBlockUser: any;
  zipZeroResults: any;
  blockedUserId: number;

  userQuery: boolean;
  communityQuery: boolean;
  appusers: IAppuser[];
  appuser: IAppuser;
  owner: any;
  isAdmin: boolean;

  constructor(
    protected blockuserService: BlockuserService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
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
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.blockeduserIdEquals != null) {
        this.nameParamBlockUser = 'blockeduserId.equals';
        this.valueParamBlockUser = params.blockeduserIdEquals;
        this.userQuery = true;
      }
      if (params.cblockedUserIdEquals != null) {
        this.nameParamBlockUser = 'cblockeduserId.equals';
        this.valueParamBlockUser = params.cblockeduserIdEquals;
        this.communityQuery = true;
      }
    });
  }

  loadAll() {
    const query = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    query['blockinguserId.equals'] = this.blockedUserId;
    this.blockuserService
      .query(query)
      .subscribe(
        (res: HttpResponse<IBlockuser[]>) => this.paginateBlockusers(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/blockuser'], {
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
      '/blockuser',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    if (this.userQuery === true) {
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
            this.blockedUserId = this.valueParamBlockUser;
            this.loadAll();
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
    this.registerChangeInBlockusers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBlockuser) {
    return item.id;
  }

  registerChangeInBlockusers() {
    this.eventSubscriber = this.eventManager.subscribe('blockuserListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private paginateBlockusers(data: IBlockuser[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.blockusers = data;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
