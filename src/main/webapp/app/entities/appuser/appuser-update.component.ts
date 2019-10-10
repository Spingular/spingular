import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAppuser, Appuser } from 'app/shared/model/appuser.model';
import { AppuserService } from './appuser.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IAppprofile } from 'app/shared/model/appprofile.model';
import { AppprofileService } from 'app/entities/appprofile/appprofile.service';
import { IAppphoto } from 'app/shared/model/appphoto.model';
import { AppphotoService } from 'app/entities/appphoto/appphoto.service';
import { IInterest } from 'app/shared/model/interest.model';
import { InterestService } from 'app/entities/interest/interest.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';
import { ICeleb } from 'app/shared/model/celeb.model';
import { CelebService } from 'app/entities/celeb/celeb.service';

@Component({
  selector: 'jhi-appuser-update',
  templateUrl: './appuser-update.component.html'
})
export class AppuserUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  appprofiles: IAppprofile[];

  appphotos: IAppphoto[];

  interests: IInterest[];

  activities: IActivity[];

  celebs: ICeleb[];

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    assignedVotesPoints: [],
    userId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected appuserService: AppuserService,
    protected userService: UserService,
    protected appprofileService: AppprofileService,
    protected appphotoService: AppphotoService,
    protected interestService: InterestService,
    protected activityService: ActivityService,
    protected celebService: CelebService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ appuser }) => {
      this.updateForm(appuser);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.appprofileService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppprofile[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppprofile[]>) => response.body)
      )
      .subscribe((res: IAppprofile[]) => (this.appprofiles = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.appphotoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppphoto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppphoto[]>) => response.body)
      )
      .subscribe((res: IAppphoto[]) => (this.appphotos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.interestService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInterest[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInterest[]>) => response.body)
      )
      .subscribe((res: IInterest[]) => (this.interests = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.activityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IActivity[]>) => mayBeOk.ok),
        map((response: HttpResponse<IActivity[]>) => response.body)
      )
      .subscribe((res: IActivity[]) => (this.activities = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.celebService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICeleb[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICeleb[]>) => response.body)
      )
      .subscribe((res: ICeleb[]) => (this.celebs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(appuser: IAppuser) {
    this.editForm.patchValue({
      id: appuser.id,
      creationDate: appuser.creationDate != null ? appuser.creationDate.format(DATE_TIME_FORMAT) : null,
      assignedVotesPoints: appuser.assignedVotesPoints,
      userId: appuser.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const appuser = this.createFromForm();
    if (appuser.id !== undefined) {
      this.subscribeToSaveResponse(this.appuserService.update(appuser));
    } else {
      this.subscribeToSaveResponse(this.appuserService.create(appuser));
    }
  }

  private createFromForm(): IAppuser {
    return {
      ...new Appuser(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      assignedVotesPoints: this.editForm.get(['assignedVotesPoints']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppuser>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackAppprofileById(index: number, item: IAppprofile) {
    return item.id;
  }

  trackAppphotoById(index: number, item: IAppphoto) {
    return item.id;
  }

  trackInterestById(index: number, item: IInterest) {
    return item.id;
  }

  trackActivityById(index: number, item: IActivity) {
    return item.id;
  }

  trackCelebById(index: number, item: ICeleb) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
