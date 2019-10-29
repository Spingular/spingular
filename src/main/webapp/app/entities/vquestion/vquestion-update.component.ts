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
import { IVquestion, Vquestion } from 'app/shared/model/vquestion.model';
import { VquestionService } from './vquestion.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IVtopic } from 'app/shared/model/vtopic.model';
import { VtopicService } from 'app/entities/vtopic/vtopic.service';

import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-vquestion-update',
  templateUrl: './vquestion-update.component.html'
})
export class VquestionUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  vtopics: IVtopic[];
  vquestion: IVquestion;
  vquestions: IVquestion[];
  creationDate: string;
  currentAccount: any;
  owner: any;
  isAdmin: boolean;
  nameParamVtopic: any;
  valueParamVtopic: any;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    vquestion: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    vquestionDescription: [null, [Validators.minLength(2), Validators.maxLength(250)]],
    appuserId: [null, Validators.required],
    vtopicId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vquestionService: VquestionService,
    protected appuserService: AppuserService,
    protected vtopicService: VtopicService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.vtopicIdEquals != null) {
        this.nameParamVtopic = 'vtopicId.equals';
        this.valueParamVtopic = params.vtopicIdEquals;
      }
    });
  }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vquestion }) => {
      this.vquestion = vquestion;
      this.creationDate = moment().format(DATE_TIME_FORMAT);
      this.vquestion.creationDate = moment(this.creationDate);
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
          if (this.valueParamVtopic != null) {
            this.vquestion.vtopicId = this.valueParamVtopic;
            this.vquestion.appuserId = this.owner;
            this.updateForm(this.vquestion);
          }
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    // this.vtopicService
    //   .query()
    //   .pipe(
    //     filter((mayBeOk: HttpResponse<IVtopic[]>) => mayBeOk.ok),
    //     map((response: HttpResponse<IVtopic[]>) => response.body)
    //   )
    //   .subscribe((res: IVtopic[]) => (this.vtopics = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vquestion: IVquestion) {
    this.editForm.patchValue({
      id: vquestion.id,
      creationDate: vquestion.creationDate != null ? vquestion.creationDate.format(DATE_TIME_FORMAT) : null,
      vquestion: vquestion.vquestion,
      vquestionDescription: vquestion.vquestionDescription,
      appuserId: vquestion.appuserId,
      vtopicId: vquestion.vtopicId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vquestion = this.createFromForm();
    if (vquestion.id !== undefined) {
      this.subscribeToSaveResponse(this.vquestionService.update(vquestion));
    } else {
      this.subscribeToSaveResponse(this.vquestionService.create(vquestion));
    }
  }

  private createFromForm(): IVquestion {
    return {
      ...new Vquestion(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      vquestion: this.editForm.get(['vquestion']).value,
      vquestionDescription: this.editForm.get(['vquestionDescription']).value,
      appuserId: this.editForm.get(['appuserId']).value,
      vtopicId: this.editForm.get(['vtopicId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVquestion>>) {
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

  trackVtopicById(index: number, item: IVtopic) {
    return item.id;
  }
}
