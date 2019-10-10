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
import { IAppprofile, Appprofile } from 'app/shared/model/appprofile.model';
import { AppprofileService } from './appprofile.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

@Component({
  selector: 'jhi-appprofile-update',
  templateUrl: './appprofile-update.component.html'
})
export class AppprofileUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    gender: [],
    phone: [null, [Validators.maxLength(20)]],
    bio: [null, [Validators.maxLength(7500)]],
    facebook: [null, [Validators.maxLength(50)]],
    twitter: [null, [Validators.maxLength(50)]],
    linkedin: [null, [Validators.maxLength(50)]],
    instagram: [null, [Validators.maxLength(50)]],
    googlePlus: [null, [Validators.maxLength(50)]],
    birthdate: [],
    civilStatus: [],
    lookingFor: [],
    purpose: [],
    physical: [],
    religion: [],
    ethnicGroup: [],
    studies: [],
    sibblings: [null, [Validators.min(-1), Validators.max(20)]],
    eyes: [],
    smoker: [],
    children: [],
    futureChildren: [],
    pet: [],
    appuserId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected appprofileService: AppprofileService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ appprofile }) => {
      this.updateForm(appprofile);
    });
    this.appuserService
      .query({ 'appprofileId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppuser[]>) => response.body)
      )
      .subscribe(
        (res: IAppuser[]) => {
          if (!this.editForm.get('appuserId').value) {
            this.appusers = res;
          } else {
            this.appuserService
              .find(this.editForm.get('appuserId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAppuser>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAppuser>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAppuser) => (this.appusers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(appprofile: IAppprofile) {
    this.editForm.patchValue({
      id: appprofile.id,
      creationDate: appprofile.creationDate != null ? appprofile.creationDate.format(DATE_TIME_FORMAT) : null,
      gender: appprofile.gender,
      phone: appprofile.phone,
      bio: appprofile.bio,
      facebook: appprofile.facebook,
      twitter: appprofile.twitter,
      linkedin: appprofile.linkedin,
      instagram: appprofile.instagram,
      googlePlus: appprofile.googlePlus,
      birthdate: appprofile.birthdate != null ? appprofile.birthdate.format(DATE_TIME_FORMAT) : null,
      civilStatus: appprofile.civilStatus,
      lookingFor: appprofile.lookingFor,
      purpose: appprofile.purpose,
      physical: appprofile.physical,
      religion: appprofile.religion,
      ethnicGroup: appprofile.ethnicGroup,
      studies: appprofile.studies,
      sibblings: appprofile.sibblings,
      eyes: appprofile.eyes,
      smoker: appprofile.smoker,
      children: appprofile.children,
      futureChildren: appprofile.futureChildren,
      pet: appprofile.pet,
      appuserId: appprofile.appuserId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const appprofile = this.createFromForm();
    if (appprofile.id !== undefined) {
      this.subscribeToSaveResponse(this.appprofileService.update(appprofile));
    } else {
      this.subscribeToSaveResponse(this.appprofileService.create(appprofile));
    }
  }

  private createFromForm(): IAppprofile {
    return {
      ...new Appprofile(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      gender: this.editForm.get(['gender']).value,
      phone: this.editForm.get(['phone']).value,
      bio: this.editForm.get(['bio']).value,
      facebook: this.editForm.get(['facebook']).value,
      twitter: this.editForm.get(['twitter']).value,
      linkedin: this.editForm.get(['linkedin']).value,
      instagram: this.editForm.get(['instagram']).value,
      googlePlus: this.editForm.get(['googlePlus']).value,
      birthdate:
        this.editForm.get(['birthdate']).value != null ? moment(this.editForm.get(['birthdate']).value, DATE_TIME_FORMAT) : undefined,
      civilStatus: this.editForm.get(['civilStatus']).value,
      lookingFor: this.editForm.get(['lookingFor']).value,
      purpose: this.editForm.get(['purpose']).value,
      physical: this.editForm.get(['physical']).value,
      religion: this.editForm.get(['religion']).value,
      ethnicGroup: this.editForm.get(['ethnicGroup']).value,
      studies: this.editForm.get(['studies']).value,
      sibblings: this.editForm.get(['sibblings']).value,
      eyes: this.editForm.get(['eyes']).value,
      smoker: this.editForm.get(['smoker']).value,
      children: this.editForm.get(['children']).value,
      futureChildren: this.editForm.get(['futureChildren']).value,
      pet: this.editForm.get(['pet']).value,
      appuserId: this.editForm.get(['appuserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppprofile>>) {
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
