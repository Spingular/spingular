import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { INewsletter, Newsletter } from 'app/shared/model/newsletter.model';
import { NewsletterService } from './newsletter.service';

@Component({
  selector: 'jhi-newsletter-update',
  templateUrl: './newsletter-update.component.html'
})
export class NewsletterUpdateComponent implements OnInit {
  isSaving: boolean;
  creationDate: string;

  private _newsletter: INewsletter;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    email: [null, [Validators.required]]
  });

  constructor(protected newsletterService: NewsletterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ newsletter }) => {
      this.newsletter = newsletter;
      this.creationDate = moment().format(DATE_TIME_FORMAT);
      this.newsletter.creationDate = moment(this.creationDate);
      this.updateForm(newsletter);
    });
  }

  updateForm(newsletter: INewsletter) {
    this.editForm.patchValue({
      id: newsletter.id,
      creationDate: newsletter.creationDate != null ? newsletter.creationDate.format(DATE_TIME_FORMAT) : null,
      email: newsletter.email
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const newsletter = this.createFromForm();
    if (newsletter.id !== undefined) {
      this.subscribeToSaveResponse(this.newsletterService.update(newsletter));
    } else {
      this.subscribeToSaveResponse(this.newsletterService.create(newsletter));
    }
  }

  private createFromForm(): INewsletter {
    return {
      ...new Newsletter(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      email: this.editForm.get(['email']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INewsletter>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  get newsletter() {
    return this._newsletter;
  }

  set newsletter(newsletter: INewsletter) {
    this._newsletter = newsletter;
    this.creationDate = moment(newsletter.creationDate).format(DATE_TIME_FORMAT);
  }
}
