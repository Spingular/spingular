import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICmessage } from 'app/shared/model/cmessage.model';
import { AccountService } from 'app/core/auth/account.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from '../.././../app/entities/community/community.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

import * as moment from 'moment';
import { Observable } from 'rxjs';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CmessageService } from './cmessage.service';

@Component({
  selector: 'jhi-cmessage',
  templateUrl: './cmessage.component.html'
})
export class CmessageComponent implements OnInit, OnDestroy {
  currentAccount: any;
  currentSearch: string;

  cmessages: ICmessage[];
  communities: ICommunity[];
  arrayCommmunities = [];
  appusers: IAppuser[];
  appuser: IAppuser;

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

  isSaving: boolean;
  creationDate: string;
  owner: any;
  isAdmin: boolean;

  constructor(
    protected cmessageService: CmessageService,
    protected communityService: CommunityService,
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
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      const query = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      };
      query['messageText.contains'] = this.currentSearch;
      query['creceiverId.in'] = this.arrayCommmunities;
      this.cmessageService.query(query).subscribe(
        (res: HttpResponse<ICmessage[]>) => {
          this.cmessages = res.body;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      return;
    }
    const query2 = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    query2['creceiverId.in'] = this.arrayCommmunities;
    this.cmessageService.query(query2).subscribe(
      (res: HttpResponse<ICmessage[]>) => {
        this.cmessages = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    return;
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/cmessage'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.currentSearch = '';
    this.router.navigate([
      '/cmessage',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/cmessage',
      {
        search: this.currentSearch,
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
          this.appusers = res.body;
          this.appuser = this.appusers[0];
          this.owner = this.appuser.id;
          this.myCmessages();
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.loadAll();
    this.registerChangeInCmessages();
  }

  myCmessages() {
    const query = {};
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.appuser.id;
    }
    this.communityService.query(query).subscribe(
      (res: HttpResponse<ICommunity[]>) => {
        this.communities = res.body;
        const query2 = {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        };
        if (this.communities != null) {
          this.arrayCommmunities = [];
          this.communities.forEach(community => {
            this.arrayCommmunities.push(community.id);
          });
          query2['creceiverId.in'] = this.arrayCommmunities;
        }
        this.cmessageService.query(query2).subscribe(
          (res2: HttpResponse<ICmessage[]>) => {
            this.cmessages = res2.body;
            this.paginateCmessages(res2.body, res2.headers);
            this.isDeliveredUpdate(this.cmessages);
          },
          (res2: HttpErrorResponse) => this.onError(res2.message)
        );
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  isDeliveredUpdate(cmessages: ICmessage[]) {
    this.isSaving = true;
    this.cmessages.forEach(cmessage => {
      this.creationDate = moment(cmessage.creationDate).format(DATE_TIME_FORMAT);
      cmessage.isDelivered = true;
      this.subscribeToSaveResponse(this.cmessageService.update(cmessage));
    });
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICmessage>>) {
    result.subscribe((res: HttpResponse<ICmessage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
  }

  private onSaveError() {
    this.isSaving = false;
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICmessage) {
    return item.id;
  }

  registerChangeInCmessages() {
    this.eventSubscriber = this.eventManager.subscribe('cmessageListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCmessages(data: ICmessage[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.cmessages = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
