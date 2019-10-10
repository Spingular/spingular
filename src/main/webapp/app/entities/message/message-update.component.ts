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
import { IMessage, Message } from 'app/shared/model/message.model';
import { MessageService } from './message.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

@Component({
  selector: 'jhi-message-update',
  templateUrl: './message-update.component.html'
})
export class MessageUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    messageText: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(8000)]],
    isDelivered: [],
    senderId: [],
    receiverId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected messageService: MessageService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ message }) => {
      this.updateForm(message);
    });
    this.appuserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppuser[]>) => response.body)
      )
      .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(message: IMessage) {
    this.editForm.patchValue({
      id: message.id,
      creationDate: message.creationDate != null ? message.creationDate.format(DATE_TIME_FORMAT) : null,
      messageText: message.messageText,
      isDelivered: message.isDelivered,
      senderId: message.senderId,
      receiverId: message.receiverId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const message = this.createFromForm();
    if (message.id !== undefined) {
      this.subscribeToSaveResponse(this.messageService.update(message));
    } else {
      this.subscribeToSaveResponse(this.messageService.create(message));
    }
  }

  private createFromForm(): IMessage {
    return {
      ...new Message(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      messageText: this.editForm.get(['messageText']).value,
      isDelivered: this.editForm.get(['isDelivered']).value,
      senderId: this.editForm.get(['senderId']).value,
      receiverId: this.editForm.get(['receiverId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessage>>) {
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
