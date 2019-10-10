import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUrllink, Urllink } from 'app/shared/model/urllink.model';
import { UrllinkService } from './urllink.service';

@Component({
  selector: 'jhi-urllink-update',
  templateUrl: './urllink-update.component.html'
})
export class UrllinkUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    linkText: [null, [Validators.required]],
    linkURL: [null, [Validators.required]]
  });

  constructor(protected urllinkService: UrllinkService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ urllink }) => {
      this.updateForm(urllink);
    });
  }

  updateForm(urllink: IUrllink) {
    this.editForm.patchValue({
      id: urllink.id,
      linkText: urllink.linkText,
      linkURL: urllink.linkURL
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const urllink = this.createFromForm();
    if (urllink.id !== undefined) {
      this.subscribeToSaveResponse(this.urllinkService.update(urllink));
    } else {
      this.subscribeToSaveResponse(this.urllinkService.create(urllink));
    }
  }

  private createFromForm(): IUrllink {
    return {
      ...new Urllink(),
      id: this.editForm.get(['id']).value,
      linkText: this.editForm.get(['linkText']).value,
      linkURL: this.editForm.get(['linkURL']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUrllink>>) {
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
