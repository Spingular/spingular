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
import { IVanswer, Vanswer } from 'app/shared/model/vanswer.model';
import { VanswerService } from './vanswer.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IVquestion } from 'app/shared/model/vquestion.model';
import { VquestionService } from 'app/entities/vquestion/vquestion.service';

@Component({
  selector: 'jhi-vanswer-update',
  templateUrl: './vanswer-update.component.html'
})
export class VanswerUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  vquestions: IVquestion[];

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
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vanswer }) => {
      this.updateForm(vanswer);
    });
    this.appuserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppuser[]>) => response.body)
      )
      .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.vquestionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVquestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVquestion[]>) => response.body)
      )
      .subscribe((res: IVquestion[]) => (this.vquestions = res), (res: HttpErrorResponse) => this.onError(res.message));
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
}
