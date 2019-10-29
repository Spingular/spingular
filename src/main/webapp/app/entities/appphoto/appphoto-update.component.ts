import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAppphoto, Appphoto } from 'app/shared/model/appphoto.model';
import { AppphotoService } from './appphoto.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

@Component({
  selector: 'jhi-appphoto-update',
  templateUrl: './appphoto-update.component.html'
})
export class AppphotoUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];
  creationDate: string;

  private _appphoto: IAppphoto;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    appuserId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected appphotoService: AppphotoService,
    protected appuserService: AppuserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ appphoto }) => {
      this.appphoto = appphoto;
      this.creationDate = moment().format(DATE_TIME_FORMAT);
      this.appphoto.creationDate = moment(this.creationDate);
      this.updateForm(appphoto);
    });
    this.appuserService
      .query({ 'appphotoId.specified': 'false' })
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

  updateForm(appphoto: IAppphoto) {
    this.editForm.patchValue({
      id: appphoto.id,
      creationDate: appphoto.creationDate != null ? appphoto.creationDate.format(DATE_TIME_FORMAT) : null,
      image: appphoto.image,
      imageContentType: appphoto.imageContentType,
      appuserId: appphoto.appuserId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const appphoto = this.createFromForm();
    if (appphoto.id !== undefined) {
      this.subscribeToSaveResponse(this.appphotoService.update(appphoto));
    } else {
      this.subscribeToSaveResponse(this.appphotoService.create(appphoto));
    }
  }

  private createFromForm(): IAppphoto {
    return {
      ...new Appphoto(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value,
      appuserId: this.editForm.get(['appuserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppphoto>>) {
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

  get appphoto() {
    return this._appphoto;
  }

  set appphoto(appphoto: IAppphoto) {
    this._appphoto = appphoto;
    this.creationDate = moment(appphoto.creationDate).format(DATE_TIME_FORMAT);
  }
}
