import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { JhiAlertService } from 'ng-jhipster';
import { IMessage, Message } from 'app/shared/model/message.model';
import { MessageService } from './message.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IBlockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from '../blockuser/blockuser.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-message-update',
  templateUrl: './message-update.component.html'
})
export class MessageUpdateComponent implements OnInit {
  private _message: IMessage;
  isSaving: boolean;

  appusers: IAppuser[] = [];
  appuser: IAppuser;
  // appusers: IAppuser[];
  creationDate: string;

  //    follows: IFollow[];
  loggedUser: IAppuser;
  blockusers: IBlockuser[];

  currentAccount: any;
  isAllowedUser: boolean;

  routeData: any;
  nameParamFollows: any;
  valueParamFollows: number;
  blockeduserId: number;
  owner: any;
  isAdmin: boolean;

  alerts: any[];

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    messageText: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(8000)]],
    isDelivered: [],
    senderId: [],
    receiverId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected messageService: MessageService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected blockuserService: BlockuserService,
    protected accountService: AccountService
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.uprofileIdEquals != null) {
        this.nameParamFollows = 'appuserId';
        this.valueParamFollows = params.appuserIdEquals;
      }
    });
  }

  // ngOnInit() {
  //   this.isSaving = false;
  //   this.activatedRoute.data.subscribe(({ message }) => {
  //     this.updateForm(message);
  //   });
  //   this.appuserService
  //     .query()
  //     .pipe(
  //       filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
  //       map((response: HttpResponse<IAppuser[]>) => response.body)
  //     )
  //     .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  // }

  ngOnInit() {
    //        this.onWarning('BLOCKED BY USER');
    this.isSaving = false;
    this.isAllowedUser = true;
    this.activatedRoute.data.subscribe(({ message }) => {
      this.message = message;
      this.creationDate = moment().format(DATE_TIME_FORMAT);
      this.message.creationDate = moment(this.creationDate);
      const query = {};
      if (this.valueParamFollows != null) {
        query['id.equals'] = Number(this.valueParamFollows);
      }
      this.appuserService.query(query).subscribe(
        (res: HttpResponse<IAppuser[]>) => {
          this.message.receiverId = res.body[0].userId;
          this.blockeduserId = res.body[0].userId;
          //                    console.log('CONSOLOG: M:ngOnInit & O: this.message.receiverId:', this.message.receiverId);
          this.accountService.identity().subscribe(account => {
            this.currentAccount = account;
            this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
            const query2 = {};
            if (this.currentAccount.id != null) {
              query2['userId.equals'] = this.currentAccount.id;
            }
            this.appuserService.query(query2).subscribe((res3: HttpResponse<IAppuser[]>) => {
              this.appusers = res3.body;
              this.appuser = this.appusers[0];
              this.owner = this.appuser.id;
              this.message.senderId = this.appuser.id;
            });
            // this.message.senderId = this.currentAccount.id;
            this.isBlockUser().subscribe(
              (res2: HttpResponse<IBlockuser[]>) => {
                this.blockusers = res2.body;
                if (this.blockusers.length > 0) {
                  this.isAllowedUser = false;
                  this.valueParamFollows = null;
                  // this.onWarning('BLOCKED BY USER');
                  // console.log('CONSOLOG: M:currentLoggedProfile & O:  this.isAllowedUser : ', this.isAllowedUser);
                  return this.blockusers[0];
                }
              },
              (res2: HttpErrorResponse) => this.onError(res2.message)
            );
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    });
  }

  private isBlockUser() {
    this.isAllowedUser = true;
    const query = {};
    if (this.currentAccount.id != null) {
      query['blockeduserId.in'] = this.blockeduserId;
      query['blockinguserId.in'] = this.currentAccount.id;
    }
    //        console.log('CONSOLOG: M:isBlockUser & O: query : ', query);
    return this.blockuserService.query(query);
  }

  updateForm(message: IMessage) {
    this.editForm.patchValue({
      id: message.id,
      creationDate: message.creationDate != null ? message.creationDate.format(DATE_TIME_FORMAT) : null,
      messageText: message.messageText,
      isDelivered: message.isDelivered,
      senderId: message.senderId,
      receiverId: message.receiverId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const message = this.createFromForm();
    if (message.id !== undefined) {
      this.subscribeToSaveResponse(this.messageService.update(message));
    } else {
      this.subscribeToSaveResponse(this.messageService.create(message));
    }
  }

  private createFromForm(): IMessage {
    return {
      ...new Message(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      messageText: this.editForm.get(['messageText']).value,
      isDelivered: this.editForm.get(['isDelivered']).value,
      senderId: this.editForm.get(['senderId']).value,
      receiverId: this.editForm.get(['receiverId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessage>>) {
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

  get message() {
    return this._message;
  }

  set message(message: IMessage) {
    this._message = message;
    this.creationDate = moment(message.creationDate).format(DATE_TIME_FORMAT);
  }
}
