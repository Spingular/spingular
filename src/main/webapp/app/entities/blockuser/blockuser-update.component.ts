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
import { IBlockuser, Blockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from './blockuser.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

@Component({
  selector: 'jhi-blockuser-update',
  templateUrl: './blockuser-update.component.html'
})
export class BlockuserUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  communities: ICommunity[];

  editForm = this.fb.group({
    id: [],
    creationDate: [],
    blockeduserId: [],
    blockinguserId: [],
    cblockeduserId: [],
    cblockinguserId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected blockuserService: BlockuserService,
    protected appuserService: AppuserService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ blockuser }) => {
      this.updateForm(blockuser);
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

  updateForm(blockuser: IBlockuser) {
    this.editForm.patchValue({
      id: blockuser.id,
      creationDate: blockuser.creationDate != null ? blockuser.creationDate.format(DATE_TIME_FORMAT) : null,
      blockeduserId: blockuser.blockeduserId,
      blockinguserId: blockuser.blockinguserId,
      cblockeduserId: blockuser.cblockeduserId,
      cblockinguserId: blockuser.cblockinguserId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const blockuser = this.createFromForm();
    if (blockuser.id !== undefined) {
      this.subscribeToSaveResponse(this.blockuserService.update(blockuser));
    } else {
      this.subscribeToSaveResponse(this.blockuserService.create(blockuser));
    }
  }

  private createFromForm(): IBlockuser {
    return {
      ...new Blockuser(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      blockeduserId: this.editForm.get(['blockeduserId']).value,
      blockinguserId: this.editForm.get(['blockinguserId']).value,
      cblockeduserId: this.editForm.get(['cblockeduserId']).value,
      cblockinguserId: this.editForm.get(['cblockinguserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlockuser>>) {
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
