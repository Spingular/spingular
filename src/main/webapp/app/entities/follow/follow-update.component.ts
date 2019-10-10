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
import { IFollow, Follow } from 'app/shared/model/follow.model';
import { FollowService } from './follow.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

@Component({
  selector: 'jhi-follow-update',
  templateUrl: './follow-update.component.html'
})
export class FollowUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  communities: ICommunity[];

  editForm = this.fb.group({
    id: [],
    creationDate: [],
    followedId: [],
    followingId: [],
    cfollowedId: [],
    cfollowingId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected followService: FollowService,
    protected appuserService: AppuserService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ follow }) => {
      this.updateForm(follow);
    });
    this.appuserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppuser[]>) => response.body)
      )
      .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.communityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICommunity[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICommunity[]>) => response.body)
      )
      .subscribe((res: ICommunity[]) => (this.communities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(follow: IFollow) {
    this.editForm.patchValue({
      id: follow.id,
      creationDate: follow.creationDate != null ? follow.creationDate.format(DATE_TIME_FORMAT) : null,
      followedId: follow.followedId,
      followingId: follow.followingId,
      cfollowedId: follow.cfollowedId,
      cfollowingId: follow.cfollowingId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const follow = this.createFromForm();
    if (follow.id !== undefined) {
      this.subscribeToSaveResponse(this.followService.update(follow));
    } else {
      this.subscribeToSaveResponse(this.followService.create(follow));
    }
  }

  private createFromForm(): IFollow {
    return {
      ...new Follow(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      followedId: this.editForm.get(['followedId']).value,
      followingId: this.editForm.get(['followingId']).value,
      cfollowedId: this.editForm.get(['cfollowedId']).value,
      cfollowingId: this.editForm.get(['cfollowingId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFollow>>) {
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

  trackAppuserById(index: number, item: IAppuser) {
    return item.id;
  }

  trackCommunityById(index: number, item: ICommunity) {
    return item.id;
  }
}
