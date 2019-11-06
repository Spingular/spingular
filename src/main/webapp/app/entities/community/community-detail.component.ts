import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiDataUtils, JhiParseLinks, JhiEventManager } from 'ng-jhipster';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommunity } from 'app/shared/model/community.model';
import { IBlog } from 'app/shared/model/blog.model';
import { BlogService } from '../blog/blog.service';
import { IFollow } from 'app/shared/model/follow.model';
import { FollowService } from '../follow/follow.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from '../notification/notification.service';
import { ICinterest } from 'app/shared/model/cinterest.model';
import { CinterestService } from '../cinterest/cinterest.service';
import { ICactivity } from 'app/shared/model/cactivity.model';
import { CactivityService } from '../cactivity/cactivity.service';
import { ICceleb } from 'app/shared/model/cceleb.model';
import { CcelebService } from '../cceleb/cceleb.service';

import { AccountService } from 'app/core/auth/account.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-community-detail',
  templateUrl: './community-detail.component.html'
})
export class CommunityDetailComponent implements OnInit {
  community: ICommunity;

  blogs: IBlog[];

  follows: IFollow[];
  private _follow: IFollow;

  appusers: IAppuser[];
  appuser: IAppuser;

  cinterests: ICinterest[];
  cactivities: ICactivity[];
  ccelebs: ICceleb[];

  loggedUser: IAppuser;

  currentAccount: any;
  isFollowing: boolean;

  loggedProfileId: number;
  creationDate: string;
  isSaving: boolean;

  userId: number;
  private _notification: INotification;
  notificationDate: string;
  notificationReason: any;
  isAdmin: boolean;
  owner: any;

  error: any;
  success: any;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any = 1;
  predicate: any = 'id';
  previousPage: any = 0;
  reverse: any = 'asc';
  id: any;

  constructor(
    protected blogService: BlogService,
    protected followService: FollowService,
    protected appuserService: AppuserService,
    protected notificationService: NotificationService,
    protected cinterestService: CinterestService,
    protected cactivityService: CactivityService,
    protected ccelebService: CcelebService,
    protected dataUtils: JhiDataUtils,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.previousPage = data.pagingParams ? data.pagingParams.page : 0;
      this.reverse = data.pagingParams ? data.pagingParams.ascending : 'asc';
      this.predicate = data.pagingParams ? data.pagingParams.predicate : 'id';
    });
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ community }) => {
      this.community = community;
      this.communitiesBlogs(community);
      this.userId = community.appuserId;
    });
    this.communityInterests();
    this.communityActivities();
    this.communityCelebs();
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
          this.loggedUser = res.body[0];
          this.isFollower();
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.isSaving = false;
    this.follow = new Object();
  }

  private communitiesBlogs(community) {
    const query = {};
    if (this.community != null) {
      query['communityId.in'] = community.id;
    }
    this.blogService.query(query).subscribe(
      (res: HttpResponse<IBlog[]>) => {
        this.paginateBlogs(res.body, res.headers);
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private communityInterests() {
    const query2 = {};
    query2['communityId.equals'] = this.community.id;
    return this.cinterestService.query(query2).subscribe(
      (res: HttpResponse<ICinterest[]>) => {
        this.cinterests = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private communityActivities() {
    const query3 = {};
    query3['communityId.equals'] = this.community.id;
    return this.cactivityService.query(query3).subscribe(
      (res: HttpResponse<ICactivity[]>) => {
        this.cactivities = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private communityCelebs() {
    const query4 = {};
    query4['communityId.equals'] = this.community.id;
    return this.ccelebService.query(query4).subscribe(
      (res: HttpResponse<ICceleb[]>) => {
        this.ccelebs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private isFollower() {
    this.isFollowing = false;
    const query = {};
    if (this.currentAccount.id != null) {
      query['followedId.in'] = this.loggedUser.id;
      query['cfollowingId.in'] = this.community.id;
    }
    return this.followService.query(query);
  }

  following() {
    this.isSaving = true;
    this.follow.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    this.follow.followedId = this.loggedUser.id;
    this.follow.cfollowingId = this.community.id;
    if (this.isFollowing === false) {
      this.subscribeToSaveResponse(this.followService.create(this.follow));
      this.notificationReason = 'FOLLOWING';
      this.createNotification(this.notificationReason);
      this.isFollowing = true;
    }
  }

  unFollowing() {
    if (this.isFollowing === true) {
      this.isFollower().subscribe(
        (res: HttpResponse<IFollow[]>) => {
          this.follows = res.body;
          if (this.follows.length > 0) {
            this.isFollowing = true;
            this.followService.delete(this.follows[0].id).subscribe(response => {
              this.notificationReason = 'UNFOLLOWING';
              this.createNotification(this.notificationReason);
              this.isFollowing = false;
            });
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }

  deleteItemFromList() {}

  private createNotification(notificationReason) {
    this.notification = new Object();
    this.isSaving = true;
    this.notification.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    this.notification.notificationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    this.notification.notificationReason = notificationReason;
    this.notification.notificationText = notificationReason;
    this.notification.isDelivered = false;
    this.notification.appuserId = this.userId;
    if (this.notification.id !== undefined) {
      this.subscribeToSaveResponse2(this.notificationService.update(this.notification));
    } else {
      this.subscribeToSaveResponse2(this.notificationService.create(this.notification));
    }
  }

  removeCommunityCinterest(cinterestId, communityId, i) {
    this.cinterests.forEach(cinterest => {
      if (cinterest.id === cinterestId) {
        cinterest.communities.forEach(community => {
          if (community.id === communityId) {
            cinterest.communities.splice(cinterest.communities.indexOf(community), 1);
            this.subscribeToSaveResponse3(this.cinterestService.update(cinterest));
            this.cinterests.splice(i, 1);
          }
        });
      }
    });
  }

  removeCommunityCactivity(cactivityId, communityId, z) {
    this.cactivities.forEach(cactivity => {
      if (cactivity.id === cactivityId) {
        cactivity.communities.forEach(community => {
          if (community.id === communityId) {
            cactivity.communities.splice(cactivity.communities.indexOf(community), 1);
            this.subscribeToSaveResponse4(this.cactivityService.update(cactivity));
            this.cactivities.splice(z, 1);
          }
        });
      }
    });
  }

  removeCommunityCceleb(ccelebId, communityId, j) {
    this.ccelebs.forEach(cceleb => {
      if (cceleb.id === ccelebId) {
        cceleb.communities.forEach(community => {
          if (community.id === communityId) {
            cceleb.communities.splice(cceleb.communities.indexOf(community), 1);
            this.subscribeToSaveResponse5(this.ccelebService.update(cceleb));
            this.ccelebs.splice(j, 1);
          }
        });
      }
    });
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IFollow>>) {
    result.subscribe((res: HttpResponse<IFollow>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveResponse2(result: Observable<HttpResponse<INotification>>) {
    result.subscribe((res: HttpResponse<INotification>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveResponse3(result: Observable<HttpResponse<ICinterest>>) {
    result.subscribe((res: HttpResponse<ICinterest>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveResponse4(result: Observable<HttpResponse<ICactivity>>) {
    result.subscribe((res: HttpResponse<ICactivity>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveResponse5(result: Observable<HttpResponse<ICceleb>>) {
    result.subscribe((res: HttpResponse<ICceleb>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveSuccess2() {
    this.isSaving = false;
  }

  private onSaveError() {
    this.isSaving = false;
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

  reload() {
    window.location.reload();
  }

  private paginateBlogs(data: IBlog[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.blogs = data;
  }

  loadPage(page) {
    this.previousPage = page;
    this.page = page;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  get follow() {
    return this._follow;
  }

  set follow(follow: IFollow) {
    this._follow = follow;
    this.creationDate = moment().format(DATE_TIME_FORMAT);
  }

  get notification() {
    return this._notification;
  }

  set notification(notification: INotification) {
    this._notification = notification;
    this.creationDate = moment().format(DATE_TIME_FORMAT);
    this.notificationDate = moment().format(DATE_TIME_FORMAT);
    this.notificationReason = '';
  }
}
