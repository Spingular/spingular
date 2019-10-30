import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAppprofile } from 'app/shared/model/appprofile.model';
import { AppprofileService } from '../appprofile/appprofile.service';
import { IFollow } from 'app/shared/model/follow.model';
import { FollowService } from '../follow/follow.service';
import { IBlockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from '../blockuser/blockuser.service';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from '../notification/notification.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IInterest } from 'app/shared/model/interest.model';
import { InterestService } from '../interest/interest.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from '../activity/activity.service';
import { ICeleb } from 'app/shared/model/celeb.model';
import { CelebService } from '../celeb/celeb.service';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-appprofile-detail',
  templateUrl: './appprofile-detail.component.html'
})
export class AppprofileDetailComponent implements OnInit {
  appusers: IAppuser[];
  appuser: IAppuser;
  appprofiles: IAppprofile[];
  appprofile: IAppprofile;

  follows: IFollow[];
  private _follow: IFollow;

  blockusers: IBlockuser[];
  private _blockuser: IBlockuser;

  interests: IInterest[] = [];
  interest: IInterest;
  activities: IActivity[];
  activity: IActivity;
  celebs: ICeleb[];
  celeb: ICeleb;

  private _notification: INotification;
  notificationDate: string;
  notificationReason: any;

  consultedUser: IAppuser;
  consultedProfile: IAppprofile;
  loggedUser: IAppuser;

  consultedUserId: number;
  loggedProfileId: number;
  loggedProfile: IAppprofile;

  currentAccount: any;
  isFollowing: boolean;
  isBlocked: boolean;
  loggedUserId: number;
  creationDate: string;
  isSaving: boolean;
  owner: any;
  isAdmin: boolean;
  nameParamUserId: any;
  valueParamUserId: any;
  uprofileUserId: any;
  appprofileUserFirstName: string;
  appprofileUserLastName: string;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected accountService: AccountService,
    protected appprofilesService: AppprofileService,
    protected interestService: InterestService,
    protected activityService: ActivityService,
    protected celebService: CelebService,
    protected appuserService: AppuserService,
    protected followService: FollowService,
    protected blockuserService: BlockuserService,
    protected notificationService: NotificationService,
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.userIdEquals != null) {
        this.nameParamUserId = 'userId.equals';
        this.valueParamUserId = params.userIdEquals;
      }
    });
  }

  ngOnInit() {
    this.accountService.identity().subscribe(
      account => {
        this.currentAccount = account;
        this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
        this.currentLoggedProfile();
        const query = {};
        if (this.currentAccount.id != null) {
          query['userId.equals'] = this.currentAccount.id;
        }
        this.appuserService.query(query).subscribe((res: HttpResponse<IAppuser[]>) => {
          this.loggedUser = res.body[0];
          this.owner = this.loggedUser.id;
          this.isFollower().subscribe(
            (res2: HttpResponse<IFollow[]>) => {
              this.follows = res2.body;
              if (this.follows.length > 0) {
                this.isFollowing = true;
              } else {
                this.isFollowing = false;
              }
            },
            (res2: HttpErrorResponse) => this.onError(res2.message)
          );
          this.isBlockUser().subscribe(
            (res3: HttpResponse<IBlockuser[]>) => {
              this.blockusers = res3.body;
              if (this.blockusers.length > 0) {
                this.isBlocked = true;
              } else {
                this.isBlocked = false;
              }
            },
            (res3: HttpErrorResponse) => this.onError(res3.message)
          );
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    if (this.valueParamUserId != null) {
      this.consultedUserId = this.valueParamUserId;
      const query = {};
      query['userId.equals'] = this.valueParamUserId;
      this.appprofilesService.query(query).subscribe(
        (res: HttpResponse<IAppprofile[]>) => {
          this.consultedProfile = res.body[0];
          this.appprofile = this.consultedProfile;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    } else {
      this.activatedRoute.data.subscribe(({ appprofile }) => {
        this.consultedProfile = appprofile;
        this.appprofile = this.consultedProfile;
      });
    }
    this.appprofileInterests();
    this.appprofileActivities();
    this.appprofileCelebs();
    this.isSaving = false;
    this.follow = new Object();
    this.blockuser = new Object();
  }

  protected appprofileInterests() {
    const query2 = {};
    query2['appuserId.equals'] = this.consultedProfile.appuserId;
    return this.interestService.query(query2).subscribe(
      (res: HttpResponse<IInterest[]>) => {
        this.interests = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  protected appprofileActivities() {
    const query3 = {};
    query3['appuserId.equals'] = this.consultedProfile.appuserId;
    return this.activityService.query(query3).subscribe(
      (res: HttpResponse<IActivity[]>) => {
        this.activities = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  protected appprofileCelebs() {
    const query4 = {};
    query4['appuserId.equals'] = this.consultedProfile.appuserId;
    return this.celebService.query(query4).subscribe(
      (res: HttpResponse<ICeleb[]>) => {
        this.celebs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  protected currentLoggedProfile() {
    const query = {};
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.currentAccount.id;
    }
    this.appprofilesService.query(query).subscribe(
      (res: HttpResponse<IAppprofile[]>) => {
        this.loggedProfile = res.body[0];
        this.loggedProfileId = res.body[0].id;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  protected isFollower() {
    this.isFollowing = false;
    const query2 = {};
    if (this.currentAccount.id != null) {
      query2['followedId.in'] = this.loggedUser.id;
      query2['followingId.in'] = this.consultedProfile.appuserId;
    }
    return this.followService.query(query2);
  }

  following() {
    this.isSaving = true;
    this.follow.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    this.follow.followingId = this.consultedProfile.appuserId;
    this.follow.followedId = this.loggedUser.id;
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

  protected createNotification(notificationReason) {
    this.notification = new Object();
    this.isSaving = true;
    this.notification.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    this.notification.notificationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    this.notification.notificationReason = notificationReason;
    this.notification.notificationText = notificationReason;
    this.notification.isDelivered = false;
    this.notification.appuserId = this.consultedProfile.appuserId;
    if (this.notification.id !== undefined) {
      this.subscribeToSaveResponse2(this.notificationService.update(this.notification));
    } else {
      this.subscribeToSaveResponse2(this.notificationService.create(this.notification));
    }
  }

  protected isBlockUser() {
    this.isBlocked = false;
    const query = {};
    if (this.currentAccount.id != null) {
      query['blockeduserId.in'] = this.consultedProfile.appuserId;
      query['blockinguserId.in'] = this.loggedUser.id;
    }
    return this.blockuserService.query(query);
  }

  blocking() {
    this.isSaving = true;
    this.blockuser.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    this.blockuser.blockeduserId = this.consultedProfile.appuserId;
    this.blockuser.blockinguserId = this.loggedUser.id;
    if (this.isBlocked === false) {
      this.subscribeToSaveResponse(this.blockuserService.create(this.blockuser));
      this.isBlocked = true;
    }
  }

  unBlocking() {
    if (this.isBlocked === true) {
      this.isBlockUser().subscribe(
        (res4: HttpResponse<IBlockuser[]>) => {
          this.blockusers = res4.body;
          if (this.blockusers.length > 0) {
            this.isBlocked = true;
            this.blockuserService.delete(this.blockusers[0].id).subscribe(response => {});
            this.isBlocked = false;
          }
        },
        (res4: HttpErrorResponse) => this.onError(res4.message)
      );
    }
  }

  removeProfileInterest(interestId: number, interestName: string, appprofileUserId: number) {
    const query = {};
    if (interestName != null) {
      query['id.equals'] = interestId;
    }
    this.interestService.query(query).subscribe((res: HttpResponse<IInterest[]>) => {
      this.interest = res.body[0];
      if (this.interests != null) {
        this.interest.appusers.forEach(appuser => {
          if (appuser.id === appprofileUserId) {
            this.interest.appusers.splice(this.interest.appusers.indexOf(appuser), 1);
            this.subscribeToSaveResponse3i(this.interestService.update(this.interest));
          }
        });
      }
    });
  }

  removeProfileActivity(activityId: number, activityName: string, appprofileUserId: number) {
    const query = {};
    if (activityName != null) {
      query['id.equals'] = activityId;
    }
    this.activityService.query(query).subscribe((res: HttpResponse<IActivity[]>) => {
      this.activity = res.body[0];
      if (this.activities != null) {
        this.activity.appusers.forEach(appuser => {
          if (appuser.id === appprofileUserId) {
            this.activity.appusers.splice(this.activity.appusers.indexOf(appuser), 1);
            this.subscribeToSaveResponse3a(this.activityService.update(this.activity));
          }
        });
      }
    });
  }

  removeProfileCeleb(celebId: number, celebName: string, appprofileUserId: number) {
    const query = {};
    if (celebName != null) {
      query['id.equals'] = celebId;
    }
    this.celebService.query(query).subscribe((res: HttpResponse<ICeleb[]>) => {
      this.celeb = res.body[0];
      if (this.celebs != null) {
        this.celeb.appusers.forEach(appuser => {
          if (appuser.id === appprofileUserId) {
            this.celeb.appusers.splice(this.celeb.appusers.indexOf(appuser), 1);
            this.subscribeToSaveResponse3c(this.celebService.update(this.celeb));
          }
        });
      }
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFollow>>) {
    result.subscribe((res: HttpResponse<IFollow>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected subscribeToSaveResponse2(result: Observable<HttpResponse<INotification>>) {
    result.subscribe((res: HttpResponse<INotification>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected subscribeToSaveResponse3i(result: Observable<HttpResponse<IInterest>>) {
    result.subscribe(
      (res: HttpResponse<IInterest>) => {
        const id = this.interests.findIndex(interest => interest.id === this.interest.id);
        this.interests.splice(id, 1);

        // for (let i = 0; i < this.interests.length; i++) {
        //   const interest = this.interests[i];
        //   if(interest.id === this.interest.id){
        //     this.interests.splice(i, 1);
        //     break;
        //   }
        // }
        this.onSaveSuccess2();
      },
      (res: HttpErrorResponse) => this.onSaveError()
    );
  }

  protected subscribeToSaveResponse3a(result: Observable<HttpResponse<IActivity>>) {
    result.subscribe(
      (res: HttpResponse<IActivity>) => {
        const id = this.activities.findIndex(activity => activity.id === this.activity.id);
        this.activities.splice(id, 1);
        this.onSaveSuccess2();
      },
      (res: HttpErrorResponse) => this.onSaveError()
    );
  }

  protected subscribeToSaveResponse3c(result: Observable<HttpResponse<ICeleb>>) {
    result.subscribe(
      (res: HttpResponse<ICeleb>) => {
        const id = this.celebs.findIndex(celeb => celeb.id === this.celeb.id);
        this.celebs.splice(id, 1);
        this.onSaveSuccess2();
      },
      (res: HttpErrorResponse) => this.onSaveError()
    );
  }

  protected subscribeToSaveResponse4(result: Observable<HttpResponse<IActivity>>) {
    result.subscribe((res: HttpResponse<IActivity>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected subscribeToSaveResponse5(result: Observable<HttpResponse<ICeleb>>) {
    result.subscribe((res: HttpResponse<ICeleb>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveSuccess2() {
    this.isSaving = false;
  }

  protected onSaveError() {
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

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  get follow() {
    return this._follow;
  }

  set follow(follow: IFollow) {
    this._follow = follow;
    this.creationDate = moment().format(DATE_TIME_FORMAT);
  }

  get blockuser() {
    return this._blockuser;
  }

  set blockuser(blockuser: IBlockuser) {
    this._blockuser = blockuser;
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
