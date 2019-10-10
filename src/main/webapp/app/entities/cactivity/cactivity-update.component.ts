import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICactivity, Cactivity } from 'app/shared/model/cactivity.model';
import { CactivityService } from './cactivity.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

@Component({
  selector: 'jhi-cactivity-update',
  templateUrl: './cactivity-update.component.html'
})
export class CactivityUpdateComponent implements OnInit {
  isSaving: boolean;

  communities: ICommunity[];

  editForm = this.fb.group({
    id: [],
    activityName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    communities: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cactivityService: CactivityService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cactivity }) => {
      this.updateForm(cactivity);
    });
    this.communityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICommunity[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICommunity[]>) => response.body)
      )
      .subscribe((res: ICommunity[]) => (this.communities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cactivity: ICactivity) {
    this.editForm.patchValue({
      id: cactivity.id,
      activityName: cactivity.activityName,
      communities: cactivity.communities
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cactivity = this.createFromForm();
    if (cactivity.id !== undefined) {
      this.subscribeToSaveResponse(this.cactivityService.update(cactivity));
    } else {
      this.subscribeToSaveResponse(this.cactivityService.create(cactivity));
    }
  }

  private createFromForm(): ICactivity {
    return {
      ...new Cactivity(),
      id: this.editForm.get(['id']).value,
      activityName: this.editForm.get(['activityName']).value,
      communities: this.editForm.get(['communities']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICactivity>>) {
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
