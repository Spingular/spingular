import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICceleb, Cceleb } from 'app/shared/model/cceleb.model';
import { CcelebService } from './cceleb.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

@Component({
  selector: 'jhi-cceleb-update',
  templateUrl: './cceleb-update.component.html'
})
export class CcelebUpdateComponent implements OnInit {
  isSaving: boolean;

  communities: ICommunity[];

  editForm = this.fb.group({
    id: [],
    celebName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    communities: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ccelebService: CcelebService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cceleb }) => {
      this.updateForm(cceleb);
    });
    this.communityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICommunity[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICommunity[]>) => response.body)
      )
      .subscribe((res: ICommunity[]) => (this.communities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cceleb: ICceleb) {
    this.editForm.patchValue({
      id: cceleb.id,
      celebName: cceleb.celebName,
      communities: cceleb.communities
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cceleb = this.createFromForm();
    if (cceleb.id !== undefined) {
      this.subscribeToSaveResponse(this.ccelebService.update(cceleb));
    } else {
      this.subscribeToSaveResponse(this.ccelebService.create(cceleb));
    }
  }

  private createFromForm(): ICceleb {
    return {
      ...new Cceleb(),
      id: this.editForm.get(['id']).value,
      celebName: this.editForm.get(['celebName']).value,
      communities: this.editForm.get(['communities']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICceleb>>) {
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

  trackCommunityById(index: number, item: ICommunity) {
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
