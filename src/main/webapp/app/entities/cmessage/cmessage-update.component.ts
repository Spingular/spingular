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
import { ICmessage, Cmessage } from 'app/shared/model/cmessage.model';
import { CmessageService } from './cmessage.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IBlockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from 'app/entities/blockuser/blockuser.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-cmessage-update',
  templateUrl: './cmessage-update.component.html'
})
export class CmessageUpdateComponent implements OnInit {
  isSaving: boolean;

  communities: ICommunity[] = [];
  community: ICommunity[];
  appusers: IAppuser[];
  appuser: IAppuser;
  blockusers: IBlockuser[];

  creationDate: string;
  currentAccount: any;
  owner: any;
  isAdmin: boolean;
  isBlocked: boolean;
  blockedByUser: string;

  nameParamFollows: any;
  valueParamFollows: number;

  routeData: any;
  alerts: any[];

  private _cmessage: ICmessage;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    messageText: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(8000)]],
    isDelivered: [],
    csenderId: [],
    creceiverId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cmessageService: CmessageService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected appuserService: AppuserService,
    protected blockuserService: BlockuserService,
    protected accountService: AccountService
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.communityIdEquals != null) {
        this.nameParamFollows = 'communityId';
        this.valueParamFollows = params.communityIdEquals;
      }
    });
  }

  // ngOnInit() {
  //   this.isSaving = false;
  //   this.activatedRoute.data.subscribe(({ cmessage }) => {
  //     this.updateForm(cmessage);
  //   });
  //   this.communityService
  //     .query()
  //     .pipe(
  //       filter((mayBeOk: HttpResponse<ICommunity[]>) => mayBeOk.ok),
  //       map((response: HttpResponse<ICommunity[]>) => response.body)
  //     )
  //     .subscribe((res: ICommunity[]) => (this.communities = res), (res: HttpErrorResponse) => this.onError(res.message));
  // }

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
          this.activatedRoute.data.subscribe(({ cmessage }) => {
            this.cmessage = cmessage;
            this.creationDate = moment().format(DATE_TIME_FORMAT);
            this.cmessage.creationDate = moment(this.creationDate);
            this.cmessage.creceiverId = Number(this.valueParamFollows);
            this.cmessage.csenderId = this.appuser.id;
            this.updateForm(cmessage);
            this.isBlockUser().subscribe(
              (res2: HttpResponse<IBlockuser[]>) => {
                this.blockusers = res2.body;
                if (this.blockusers.length > 0) {
                  this.isBlocked = true;
                  this.valueParamFollows = null;
                  this.onWarning('BLOCKED BY USER');
                  return this.blockusers[0];
                }
              },
              (res2: HttpErrorResponse) => this.onError(res2.message)
            );
          });
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.isSaving = false;
    this.communityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICommunity[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICommunity[]>) => response.body)
      )
      .subscribe((res: ICommunity[]) => (this.communities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  private isBlockUser() {
    this.isBlocked = false;
    const query = {};
    if (this.currentAccount.id != null) {
      query['blockeduserId.in'] = Number(this.valueParamFollows);
      query['blockinguserId.in'] = this.currentAccount.id;
    }
    return this.blockuserService.query(query);
  }

  updateForm(cmessage: ICmessage) {
    this.editForm.patchValue({
      id: cmessage.id,
      creationDate: cmessage.creationDate != null ? cmessage.creationDate.format(DATE_TIME_FORMAT) : null,
      messageText: cmessage.messageText,
      isDelivered: cmessage.isDelivered,
      csenderId: cmessage.csenderId,
      creceiverId: cmessage.creceiverId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cmessage = this.createFromForm();
    if (cmessage.id !== undefined) {
      this.subscribeToSaveResponse(this.cmessageService.update(cmessage));
    } else {
      this.subscribeToSaveResponse(this.cmessageService.create(cmessage));
    }
  }

  private createFromForm(): ICmessage {
    return {
      ...new Cmessage(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      messageText: this.editForm.get(['messageText']).value,
      isDelivered: this.editForm.get(['isDelivered']).value,
      csenderId: this.editForm.get(['csenderId']).value,
      creceiverId: this.editForm.get(['creceiverId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICmessage>>) {
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

  private onWarning(errorMessage: string) {
    this.alerts = [];
    this.alerts.push(
      this.jhiAlertService.addAlert(
        {
          type: 'info',
          msg: errorMessage,
          timeout: 5000,
          toast: false,
          scoped: true
        },
        this.alerts
      )
    );
  }

  trackCommunityById(index: number, item: ICommunity) {
    return item.id;
  }

  get cmessage() {
    return this._cmessage;
  }

  set cmessage(cmessage: ICmessage) {
    this._cmessage = cmessage;
    this.creationDate = moment(cmessage.creationDate).format(DATE_TIME_FORMAT);
  }
}
