import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVtopic } from 'app/shared/model/vtopic.model';
import { IVquestion } from 'app/shared/model/vquestion.model';
import { VquestionService } from '../vquestion/vquestion.service';
import { IVthumb } from 'app/shared/model/vthumb.model';
import { VthumbService } from '../vthumb/vthumb.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-vtopic-detail',
  templateUrl: './vtopic-detail.component.html'
})
export class VtopicDetailComponent implements OnInit {
  values: IVquestion[];
  vtopic: IVtopic;
  vquestions: IVquestion[];
  appusers: IAppuser[];
  appuser: IAppuser;
  items = [];
  private _vthumb: IVthumb = {};

  menu = [];

  itemsPerPage: any;
  page: any;
  predicate: any;
  reverse: any;

  isSaving: boolean;
  creationDate: string;
  currentAccount: any;
  owner: any;
  isAdmin: boolean;
  accc = true;

  constructor(
    protected vquestionService: VquestionService,
    protected vthumbService: VthumbService,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected appuserService: AppuserService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vtopic }) => {
      this.vtopic = vtopic;
      const query = {};
      if (this.vtopic != null) {
        query['vtopicId.equals'] = vtopic.id;
      }
      this.vquestionService.query(query).subscribe(
        (res: HttpResponse<IVquestion[]>) => {
          this.vquestions = res.body;
          this.menu = Array(this.vquestions.length).fill(false);
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    });
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
          // this.loggedUser = res.body[0];
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  changeMenu(i) {
    this.menu[i] = !this.menu[i];
  }

  registerQuestionThumbUp(number) {
    this.isSaving = true;
    this.vthumb = this.vthumb;
    if (number !== undefined) {
      this.vthumb.vthumbUp = true;
      this.vthumb.vthumbDown = false;
      this.vthumb.appuserId = this.owner;
      this.vthumb.vquestionId = number;
      this.vthumb.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
      this.subscribeToSaveResponse(this.vthumbService.create(this.vthumb));
    }
  }

  registerQuestionThumbDown(number) {
    this.isSaving = true;
    this.vthumb = this.vthumb;
    if (number !== undefined) {
      this.vthumb.vthumbUp = false;
      this.vthumb.vthumbDown = true;
      this.vthumb.appuserId = this.owner;
      this.vthumb.vquestionId = number;
      this.vthumb.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
      this.subscribeToSaveResponse(this.vthumbService.create(this.vthumb));
      this.vquestions[number].vthumbs.push(this.vthumb);
    }
  }

  registerAnswerThumbUp(number) {
    this.isSaving = true;
    this.vthumb = this.vthumb;
    if (number !== undefined) {
      this.vthumb.vthumbUp = true;
      this.vthumb.vthumbDown = false;
      this.vthumb.appuserId = this.owner;
      this.vthumb.vanswerId = number;
      this.vthumb.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
      this.subscribeToSaveResponse(this.vthumbService.create(this.vthumb));
    }
  }

  registerAnswerThumbDown(number) {
    this.isSaving = true;
    this.vthumb = this.vthumb;
    if (number !== undefined) {
      this.vthumb.vthumbUp = false;
      this.vthumb.vthumbDown = true;
      this.vthumb.appuserId = this.owner;
      this.vthumb.vanswerId = number;
      this.creationDate = this.vthumb.creationDate != null ? this.vthumb.creationDate.format(DATE_TIME_FORMAT) : null;
      this.vthumb.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
      this.subscribeToSaveResponse(this.vthumbService.create(this.vthumb));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IVthumb>>) {
    result.subscribe((res: HttpResponse<IVthumb>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.reload();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  get vthumb() {
    return this._vthumb;
  }

  set vthumb(vthumb: IVthumb) {
    this._vthumb = vthumb;
    this.creationDate = moment(vthumb.creationDate).format(DATE_TIME_FORMAT);
    this.vthumb.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
  }

  previousState() {
    window.history.back();
  }

  reload() {
    window.location.reload();
  }

  accordionAddItem(i) {
    if (this.items.includes(i)) {
      const index = this.items.indexOf(i);
      this.items.splice(index, 1);
    } else {
      this.items.push(i);
    }
  }

  accordionShowItem(i) {
    return this.items.includes(i);
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
