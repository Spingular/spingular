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
import { ICmessage, Cmessage } from 'app/shared/model/cmessage.model';
import { CmessageService } from './cmessage.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

@Component({
  selector: 'jhi-cmessage-update',
  templateUrl: './cmessage-update.component.html'
})
export class CmessageUpdateComponent implements OnInit {
  isSaving: boolean;

  communities: ICommunity[];

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    messageText: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(8000)]],
    isDelivered: [],
    csenderId: [],
    creceiverId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cmessageService: CmessageService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cmessage }) => {
      this.updateForm(cmessage);
    });
    this.communityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICommunity[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICommunity[]>) => response.body)
      )
      .subscribe((res: ICommunity[]) => (this.communities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cmessage: ICmessage) {
    this.editForm.patchValue({
      id: cmessage.id,
      creationDate: cmessage.creationDate != null ? cmessage.creationDate.format(DATE_TIME_FORMAT) : null,
      messageText: cmessage.messageText,
      isDelivered: cmessage.isDelivered,
      csenderId: cmessage.csenderId,
      creceiverId: cmessage.creceiverId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cmessage = this.createFromForm();
    if (cmessage.id !== undefined) {
      this.subscribeToSaveResponse(this.cmessageService.update(cmessage));
    } else {
      this.subscribeToSaveResponse(this.cmessageService.create(cmessage));
    }
  }

  private createFromForm(): ICmessage {
    return {
      ...new Cmessage(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      messageText: this.editForm.get(['messageText']).value,
      isDelivered: this.editForm.get(['isDelivered']).value,
      csenderId: this.editForm.get(['csenderId']).value,
      creceiverId: this.editForm.get(['creceiverId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICmessage>>) {
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
}
