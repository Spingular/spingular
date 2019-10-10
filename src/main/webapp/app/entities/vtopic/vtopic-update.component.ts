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
import { IVtopic, Vtopic } from 'app/shared/model/vtopic.model';
import { VtopicService } from './vtopic.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

@Component({
  selector: 'jhi-vtopic-update',
  templateUrl: './vtopic-update.component.html'
})
export class VtopicUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    vtopicTitle: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
    vtopicDescription: [null, [Validators.minLength(2), Validators.maxLength(250)]],
    appuserId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vtopicService: VtopicService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vtopic }) => {
      this.updateForm(vtopic);
    });
    this.appuserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppuser[]>) => response.body)
      )
      .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vtopic: IVtopic) {
    this.editForm.patchValue({
      id: vtopic.id,
      creationDate: vtopic.creationDate != null ? vtopic.creationDate.format(DATE_TIME_FORMAT) : null,
      vtopicTitle: vtopic.vtopicTitle,
      vtopicDescription: vtopic.vtopicDescription,
      appuserId: vtopic.appuserId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vtopic = this.createFromForm();
    if (vtopic.id !== undefined) {
      this.subscribeToSaveResponse(this.vtopicService.update(vtopic));
    } else {
      this.subscribeToSaveResponse(this.vtopicService.create(vtopic));
    }
  }

  private createFromForm(): IVtopic {
    return {
      ...new Vtopic(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      vtopicTitle: this.editForm.get(['vtopicTitle']).value,
      vtopicDescription: this.editForm.get(['vtopicDescription']).value,
      appuserId: this.editForm.get(['appuserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVtopic>>) {
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
}
