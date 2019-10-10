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
import { IVthumb, Vthumb } from 'app/shared/model/vthumb.model';
import { VthumbService } from './vthumb.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IVquestion } from 'app/shared/model/vquestion.model';
import { VquestionService } from 'app/entities/vquestion/vquestion.service';
import { IVanswer } from 'app/shared/model/vanswer.model';
import { VanswerService } from 'app/entities/vanswer/vanswer.service';

@Component({
  selector: 'jhi-vthumb-update',
  templateUrl: './vthumb-update.component.html'
})
export class VthumbUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  vquestions: IVquestion[];

  vanswers: IVanswer[];

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    vthumbUp: [],
    vthumbDown: [],
    appuserId: [null, Validators.required],
    vquestionId: [],
    vanswerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vthumbService: VthumbService,
    protected appuserService: AppuserService,
    protected vquestionService: VquestionService,
    protected vanswerService: VanswerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vthumb }) => {
      this.updateForm(vthumb);
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
    this.vanswerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVanswer[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVanswer[]>) => response.body)
      )
      .subscribe((res: IVanswer[]) => (this.vanswers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vthumb: IVthumb) {
    this.editForm.patchValue({
      id: vthumb.id,
      creationDate: vthumb.creationDate != null ? vthumb.creationDate.format(DATE_TIME_FORMAT) : null,
      vthumbUp: vthumb.vthumbUp,
      vthumbDown: vthumb.vthumbDown,
      appuserId: vthumb.appuserId,
      vquestionId: vthumb.vquestionId,
      vanswerId: vthumb.vanswerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vthumb = this.createFromForm();
    if (vthumb.id !== undefined) {
      this.subscribeToSaveResponse(this.vthumbService.update(vthumb));
    } else {
      this.subscribeToSaveResponse(this.vthumbService.create(vthumb));
    }
  }

  private createFromForm(): IVthumb {
    return {
      ...new Vthumb(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      vthumbUp: this.editForm.get(['vthumbUp']).value,
      vthumbDown: this.editForm.get(['vthumbDown']).value,
      appuserId: this.editForm.get(['appuserId']).value,
      vquestionId: this.editForm.get(['vquestionId']).value,
      vanswerId: this.editForm.get(['vanswerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVthumb>>) {
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

  trackVanswerById(index: number, item: IVanswer) {
    return item.id;
  }
}
