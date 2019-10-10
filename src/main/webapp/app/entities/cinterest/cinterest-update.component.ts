import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICinterest, Cinterest } from 'app/shared/model/cinterest.model';
import { CinterestService } from './cinterest.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

@Component({
  selector: 'jhi-cinterest-update',
  templateUrl: './cinterest-update.component.html'
})
export class CinterestUpdateComponent implements OnInit {
  isSaving: boolean;

  communities: ICommunity[];

  editForm = this.fb.group({
    id: [],
    interestName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    communities: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cinterestService: CinterestService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cinterest }) => {
      this.updateForm(cinterest);
    });
    this.communityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICommunity[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICommunity[]>) => response.body)
      )
      .subscribe((res: ICommunity[]) => (this.communities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cinterest: ICinterest) {
    this.editForm.patchValue({
      id: cinterest.id,
      interestName: cinterest.interestName,
      communities: cinterest.communities
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cinterest = this.createFromForm();
    if (cinterest.id !== undefined) {
      this.subscribeToSaveResponse(this.cinterestService.update(cinterest));
    } else {
      this.subscribeToSaveResponse(this.cinterestService.create(cinterest));
    }
  }

  private createFromForm(): ICinterest {
    return {
      ...new Cinterest(),
      id: this.editForm.get(['id']).value,
      interestName: this.editForm.get(['interestName']).value,
      communities: this.editForm.get(['communities']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICinterest>>) {
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
