import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICeleb, Celeb } from 'app/shared/model/celeb.model';
import { CelebService } from './celeb.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

@Component({
  selector: 'jhi-celeb-update',
  templateUrl: './celeb-update.component.html'
})
export class CelebUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  editForm = this.fb.group({
    id: [],
    celebName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    appusers: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected celebService: CelebService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ celeb }) => {
      this.updateForm(celeb);
    });
    this.appuserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppuser[]>) => response.body)
      )
      .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(celeb: ICeleb) {
    this.editForm.patchValue({
      id: celeb.id,
      celebName: celeb.celebName,
      appusers: celeb.appusers
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const celeb = this.createFromForm();
    if (celeb.id !== undefined) {
      this.subscribeToSaveResponse(this.celebService.update(celeb));
    } else {
      this.subscribeToSaveResponse(this.celebService.create(celeb));
    }
  }

  private createFromForm(): ICeleb {
    return {
      ...new Celeb(),
      id: this.editForm.get(['id']).value,
      celebName: this.editForm.get(['celebName']).value,
      appusers: this.editForm.get(['appusers']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICeleb>>) {
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
