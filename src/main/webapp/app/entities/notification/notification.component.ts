import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { AccountService } from 'app/core/auth/account.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-notification',
  templateUrl: './notification.component.html'
})
export class NotificationComponent implements OnInit, OnDestroy {
  currentAccount: any;
  notifications: INotification[];
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

  private _notification: INotification;
  currentSearch: string;
  pparamNotificationUserId: any;
  owner: any;
  isAdmin: boolean;
  creationDate: string;
  notificationDate: string;
  isSaving: boolean;
  appusers: IAppuser[];
  appuser: IAppuser;

  constructor(
    protected notificationService: NotificationService,
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

  // loadAll() {
  //   this.notificationService
  //     .query({
  //       page: this.page - 1,
  //       size: this.itemsPerPage,
  //       sort: this.sort()
  //     })
  //     .subscribe((res: HttpResponse<INotification[]>) => this.paginateNotifications(res.body, res.headers));
  // }

  loadAll() {
    if (this.currentSearch) {
      const query = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      };
      query['notificationText.contains'] = this.currentSearch;
      query['userId.equals'] = this.currentAccount.id;
      this.notificationService.query(query).subscribe(
        (res: HttpResponse<INotification[]>) => {
          this.notifications = res.body;
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
    query2['userId.equals'] = this.currentAccount.id;
    this.notificationService.query(query2).subscribe(
      (res: HttpResponse<INotification[]>) => {
        this.notifications = res.body;
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
    this.router.navigate(['/notification'], {
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
      '/notification',
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
      '/notification',
      {
        search: this.currentSearch,
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
  //   this.registerChangeInNotifications();
  // }
  ngOnInit() {
    this.isSaving = false;
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
          this.myNotifications();
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    // this.accountService.identity().then(account => {
    //     this.currentAccount = account;
    //     this.owner = account.id;
    //     this.myNotifications();
    // });
    // this.userService.query().subscribe(
    //     (res: HttpResponse<IUser[]>) => {
    //         this.users = res.body;
    //     },
    //     (res: HttpErrorResponse) => this.onError(res.message)
    // );
    this.registerChangeInNotifications();
  }

  myNotifications() {
    const query = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    if (this.currentAccount.id != null) {
      query['userId.equals'] = this.currentAccount.id;
    }
    this.notificationService.query(query).subscribe(
      (res: HttpResponse<INotification[]>) => {
        this.paginateNotifications(res.body, res.headers);
        this.notifications = res.body;
        //                console.log('CONSOLOG: M:isDeliveredUpdate & O: res.body : ', res.body);
        this.isDeliveredUpdate(this.notifications);
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  isDeliveredUpdate(notifications: INotification[]) {
    this.isSaving = true;
    this.notifications.forEach(notification => {
      //            console.log('CONSOLOG: M:isDeliveredUpdate & O: notifications PRE-Date : ', notifications);
      this.notificationDate = moment(notification.notificationDate).format(DATE_TIME_FORMAT);
      //            console.log('CONSOLOG: M:isDeliveredUpdate & O: this.notificationDate : ', this.notificationDate);
      //            console.log('CONSOLOG: M:isDeliveredUpdate & O: notifications POST-Date : ', notifications);
      notification.isDelivered = true;
      //            this.notificationService.update(notification);
      this.subscribeToSaveResponse(this.notificationService.update(notification));
      //            this.subscribeToSaveResponse(this.notificationService.update(notification));
      //            console.log('CONSOLOG: M:isDeliveredUpdate & O: notifications : ', notifications);
    });
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<INotification>>) {
    result.subscribe((res: HttpResponse<INotification>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackId(index: number, item: INotification) {
    return item.id;
  }

  registerChangeInNotifications() {
    this.eventSubscriber = this.eventManager.subscribe('notificationListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateNotifications(data: INotification[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.notifications = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
