import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInterest, Interest } from 'app/shared/model/interest.model';
import { InterestService } from './interest.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

@Component({
  selector: 'jhi-interest-update',
  templateUrl: './interest-update.component.html'
})
export class InterestUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  editForm = this.fb.group({
    id: [],
    interestName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    appusers: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected interestService: InterestService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ interest }) => {
      this.updateForm(interest);
    });
    this.appuserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppuser[]>) => response.body)
      )
      .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(interest: IInterest) {
    this.editForm.patchValue({
      id: interest.id,
      interestName: interest.interestName,
      appusers: interest.appusers
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const interest = this.createFromForm();
    if (interest.id !== undefined) {
      this.subscribeToSaveResponse(this.interestService.update(interest));
    } else {
      this.subscribeToSaveResponse(this.interestService.create(interest));
    }
  }

  private createFromForm(): IInterest {
    return {
      ...new Interest(),
      id: this.editForm.get(['id']).value,
      interestName: this.editForm.get(['interestName']).value,
      appusers: this.editForm.get(['appusers']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterest>>) {
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

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
