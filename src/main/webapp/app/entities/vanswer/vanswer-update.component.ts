import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IVanswer, Vanswer } from 'app/shared/model/vanswer.model';
import { VanswerService } from './vanswer.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IVquestion } from 'app/shared/model/vquestion.model';
import { VquestionService } from 'app/entities/vquestion/vquestion.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-vanswer-update',
  templateUrl: './vanswer-update.component.html'
})
export class VanswerUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  vquestions: IVquestion[];

  vanswers: IVanswer[];
  owner: any;
  isAdmin: boolean;

  creationDate: string;

  currentAccount: any;

  nameParamVquestion: any;
  valueParamVquestion: any;

  private _vanswer: IVanswer;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    urlVanswer: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(500)]],
    accepted: [],
    appuserId: [null, Validators.required],
    vquestionId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vanswerService: VanswerService,
    protected appuserService: AppuserService,
    protected vquestionService: VquestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.vquestionIdEquals != null) {
        this.nameParamVquestion = 'vquestionId.equals';
        this.valueParamVquestion = params.vquestionIdEquals;
      }
    });
  }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vanswer }) => {
      this.vanswer = vanswer;
      this.creationDate = moment().format(DATE_TIME_FORMAT);
      this.vanswer.creationDate = moment(this.creationDate);
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
          this.appusers = res.body;
          this.owner = res.body[0].id;
          if (this.valueParamVquestion != null) {
            this.vanswer.vquestionId = this.valueParamVquestion;
            this.vanswer.appuserId = this.owner;
            this.updateForm(this.vanswer);
          }
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(vanswer: IVanswer) {
    this.editForm.patchValue({
      id: vanswer.id,
      creationDate: vanswer.creationDate != null ? vanswer.creationDate.format(DATE_TIME_FORMAT) : null,
      urlVanswer: vanswer.urlVanswer,
      accepted: vanswer.accepted,
      appuserId: vanswer.appuserId,
      vquestionId: vanswer.vquestionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.vanswer.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
    const vanswer = this.createFromForm();
    if (vanswer.id !== undefined) {
      this.subscribeToSaveResponse(this.vanswerService.update(vanswer));
    } else {
      this.subscribeToSaveResponse(this.vanswerService.create(vanswer));
    }
  }

  private createFromForm(): IVanswer {
    return {
      ...new Vanswer(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      urlVanswer: this.editForm.get(['urlVanswer']).value,
      accepted: this.editForm.get(['accepted']).value,
      appuserId: this.editForm.get(['appuserId']).value,
      vquestionId: this.editForm.get(['vquestionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVanswer>>) {
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

  trackVquestionById(index: number, item: IVquestion) {
    return item.id;
  }

  get vanswer() {
    return this._vanswer;
  }

  set vanswer(vanswer: IVanswer) {
    this._vanswer = vanswer;
    this.creationDate = moment(vanswer.creationDate).format(DATE_TIME_FORMAT);
  }
}
