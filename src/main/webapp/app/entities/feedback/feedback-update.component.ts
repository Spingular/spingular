import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IFeedback, Feedback } from 'app/shared/model/feedback.model';
import { FeedbackService } from './feedback.service';

@Component({
  selector: 'jhi-feedback-update',
  templateUrl: './feedback-update.component.html'
})
export class FeedbackUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    name: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    email: [null, [Validators.required]],
    feedback: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(5000)]]
  });

  constructor(protected feedbackService: FeedbackService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ feedback }) => {
      this.updateForm(feedback);
    });
  }

  updateForm(feedback: IFeedback) {
    this.editForm.patchValue({
      id: feedback.id,
      creationDate: feedback.creationDate != null ? feedback.creationDate.format(DATE_TIME_FORMAT) : null,
      name: feedback.name,
      email: feedback.email,
      feedback: feedback.feedback
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const feedback = this.createFromForm();
    if (feedback.id !== undefined) {
      this.subscribeToSaveResponse(this.feedbackService.update(feedback));
    } else {
      this.subscribeToSaveResponse(this.feedbackService.create(feedback));
    }
  }

  private createFromForm(): IFeedback {
    return {
      ...new Feedback(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      name: this.editForm.get(['name']).value,
      email: this.editForm.get(['email']).value,
      feedback: this.editForm.get(['feedback']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedback>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
