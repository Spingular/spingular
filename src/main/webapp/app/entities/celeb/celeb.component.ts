// import { Component, OnInit, OnDestroy } from '@angular/core';
// import { HttpHeaders, HttpResponse } from '@angular/common/http';
// import { ActivatedRoute, Router } from '@angular/router';
// import { Subscription } from 'rxjs';
// // eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { filter, map } from 'rxjs/operators';
// import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

// import { ICeleb } from 'app/shared/model/celeb.model';
// import { AccountService } from 'app/core/auth/account.service';

// import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
// import { CelebService } from './celeb.service';

// @Component({
//   selector: 'jhi-celeb',
//   templateUrl: './celeb.component.html'
// })
// export class CelebComponent implements OnInit, OnDestroy {
//   currentAccount: any;
//   celebs: ICeleb[];
//   error: any;
//   success: any;
//   eventSubscriber: Subscription;
//   routeData: any;
//   links: any;
//   totalItems: any;
//   itemsPerPage: any;
//   page: any;
//   predicate: any;
//   previousPage: any;
//   reverse: any;

//   constructor(
//     protected celebService: CelebService,
//     protected parseLinks: JhiParseLinks,
//     protected accountService: AccountService,
//     protected activatedRoute: ActivatedRoute,
//     protected router: Router,
//     protected eventManager: JhiEventManager
//   ) {
//     this.itemsPerPage = ITEMS_PER_PAGE;
//     this.routeData = this.activatedRoute.data.subscribe(data => {
//       this.page = data.pagingParams.page;
//       this.previousPage = data.pagingParams.page;
//       this.reverse = data.pagingParams.ascending;
//       this.predicate = data.pagingParams.predicate;
//     });
//   }

//   loadAll() {
//     this.celebService
//       .query({
//         page: this.page - 1,
//         size: this.itemsPerPage,
//         sort: this.sort()
//       })
//       .subscribe((res: HttpResponse<ICeleb[]>) => this.paginateCelebs(res.body, res.headers));
//   }

//   loadPage(page: number) {
//     if (page !== this.previousPage) {
//       this.previousPage = page;
//       this.transition();
//     }
//   }

//   transition() {
//     this.router.navigate(['/celeb'], {
//       queryParams: {
//         page: this.page,
//         size: this.itemsPerPage,
//         sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
//       }
//     });
//     this.loadAll();
//   }

//   clear() {
//     this.page = 0;
//     this.router.navigate([
//       '/celeb',
//       {
//         page: this.page,
//         sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
//       }
//     ]);
//     this.loadAll();
//   }

//   ngOnInit() {
//     this.loadAll();
//     this.accountService.identity().subscribe(account => {
//       this.currentAccount = account;
//     });
//     this.registerChangeInCelebs();
//   }

//   ngOnDestroy() {
//     this.eventManager.destroy(this.eventSubscriber);
//   }

//   trackId(index: number, item: ICeleb) {
//     return item.id;
//   }

//   registerChangeInCelebs() {
//     this.eventSubscriber = this.eventManager.subscribe('celebListModification', response => this.loadAll());
//   }

//   sort() {
//     const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
//     if (this.predicate !== 'id') {
//       result.push('id');
//     }
//     return result;
//   }

//   protected paginateCelebs(data: ICeleb[], headers: HttpHeaders) {
//     this.links = this.parseLinks.parse(headers.get('link'));
//     this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
//     this.celebs = data;
//   }
// }

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICeleb } from 'app/shared/model/celeb.model';
import { CelebService } from './celeb.service';

import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-celeb',
  templateUrl: './celeb.component.html'
})
export class CelebComponent implements OnInit, OnDestroy {
  currentAccount: any;
  celebs: ICeleb[];
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
  currentSearch: string;
  owner: any;
  isAdmin: boolean;

  constructor(
    protected celebService: CelebService,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected appuserService: AppuserService,
    protected jhiAlertService: JhiAlertService
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
      this.celebService
        .query({
          page: this.page - 1,
          'celebName.contains': this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ICeleb[]>) => this.paginateCelebs(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.celebService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICeleb[]>) => this.paginateCelebs(res.body, res.headers),
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
    this.router.navigate(['/celeb'], {
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
      '/celeb',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  search(query: string) {
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/celeb',
      {
        search: this.currentSearch,
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

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
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.registerChangeInCelebs();
  }

  myCelebs() {
    const query = {};
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.owner;
    }
    this.celebService.query(query).subscribe(
      (res: HttpResponse<ICeleb[]>) => {
        this.celebs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICeleb) {
    return item.id;
  }

  registerChangeInCelebs() {
    this.eventSubscriber = this.eventManager.subscribe('celebListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCelebs(data: ICeleb[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.celebs = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
