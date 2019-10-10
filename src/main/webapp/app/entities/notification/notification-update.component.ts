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
import { INotification, Notification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

@Component({
  selector: 'jhi-notification-update',
  templateUrl: './notification-update.component.html'
})
export class NotificationUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    notificationDate: [],
    notificationReason: [null, [Validators.required]],
    notificationText: [null, [Validators.minLength(2), Validators.maxLength(100)]],
    isDelivered: [],
    appuserId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected notificationService: NotificationService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ notification }) => {
      this.updateForm(notification);
    });
    this.appuserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppuser[]>) => response.body)
      )
      .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(notification: INotification) {
    this.editForm.patchValue({
      id: notification.id,
      creationDate: notification.creationDate != null ? notification.creationDate.format(DATE_TIME_FORMAT) : null,
      notificationDate: notification.notificationDate != null ? notification.notificationDate.format(DATE_TIME_FORMAT) : null,
      notificationReason: notification.notificationReason,
      notificationText: notification.notificationText,
      isDelivered: notification.isDelivered,
      appuserId: notification.appuserId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const notification = this.createFromForm();
    if (notification.id !== undefined) {
      this.subscribeToSaveResponse(this.notificationService.update(notification));
    } else {
      this.subscribeToSaveResponse(this.notificationService.create(notification));
    }
  }

  private createFromForm(): INotification {
    return {
      ...new Notification(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      notificationDate:
        this.editForm.get(['notificationDate']).value != null
          ? moment(this.editForm.get(['notificationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      notificationReason: this.editForm.get(['notificationReason']).value,
      notificationText: this.editForm.get(['notificationText']).value,
      isDelivered: this.editForm.get(['isDelivered']).value,
      appuserId: this.editForm.get(['appuserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotification>>) {
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
