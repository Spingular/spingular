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
import { ICommunity, Community } from 'app/shared/model/community.model';
import { CommunityService } from './community.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { ICinterest } from 'app/shared/model/cinterest.model';
import { CinterestService } from 'app/entities/cinterest/cinterest.service';
import { ICactivity } from 'app/shared/model/cactivity.model';
import { CactivityService } from 'app/entities/cactivity/cactivity.service';
import { ICceleb } from 'app/shared/model/cceleb.model';
import { CcelebService } from 'app/entities/cceleb/cceleb.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-community-update',
  templateUrl: './community-update.component.html'
})
export class CommunityUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];
  appuser: IAppuser;

  cinterests: ICinterest[];

  cactivities: ICactivity[];

  ccelebs: ICceleb[];

  creationDate: string;
  currentAccount: any;
  owner: any;
  isAdmin: boolean;

  private _community: ICommunity;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    communityName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    communityDescription: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(7500)]],
    image: [],
    imageContentType: [],
    isActive: [],
    appuserId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected communityService: CommunityService,
    protected appuserService: AppuserService,
    protected cinterestService: CinterestService,
    protected cactivityService: CactivityService,
    protected ccelebService: CcelebService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ community }) => {
      this.community = community;
      this.creationDate = moment().format(DATE_TIME_FORMAT);
      this.community.creationDate = moment(this.creationDate);
      this.updateForm(community);
    });
    this.accountService.identity().subscribe(
      account => {
        this.currentAccount = account;
        this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
        const query = {};
        if (this.currentAccount.id != null) {
          query['userId.equals'] = this.currentAccount.id;
        }
        this.appuserService.query(query).subscribe((res: HttpResponse<IAppuser[]>) => {
          this.appusers = res.body;
          this.appuser = this.appusers[0];
          this.owner = res.body[0].id;
          // this.loggedUser = res.body[0];
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.cinterestService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICinterest[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICinterest[]>) => response.body)
      )
      .subscribe((res: ICinterest[]) => (this.cinterests = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.cactivityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICactivity[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICactivity[]>) => response.body)
      )
      .subscribe((res: ICactivity[]) => (this.cactivities = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ccelebService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICceleb[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICceleb[]>) => response.body)
      )
      .subscribe((res: ICceleb[]) => (this.ccelebs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(community: ICommunity) {
    this.editForm.patchValue({
      id: community.id,
      creationDate: community.creationDate != null ? community.creationDate.format(DATE_TIME_FORMAT) : null,
      communityName: community.communityName,
      communityDescription: community.communityDescription,
      image: community.image,
      imageContentType: community.imageContentType,
      isActive: community.isActive,
      appuserId: community.appuserId
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
    const community = this.createFromForm();
    if (community.id !== undefined) {
      this.subscribeToSaveResponse(this.communityService.update(community));
    } else {
      this.subscribeToSaveResponse(this.communityService.create(community));
    }
  }

  private createFromForm(): ICommunity {
    return {
      ...new Community(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      communityName: this.editForm.get(['communityName']).value,
      communityDescription: this.editForm.get(['communityDescription']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value,
      isActive: this.editForm.get(['isActive']).value,
      appuserId: this.editForm.get(['appuserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommunity>>) {
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

  trackCinterestById(index: number, item: ICinterest) {
    return item.id;
  }

  trackCactivityById(index: number, item: ICactivity) {
    return item.id;
  }

  trackCcelebById(index: number, item: ICceleb) {
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

  get community() {
    return this._community;
  }

  set community(community: ICommunity) {
    this._community = community;
    this.creationDate = moment(community.creationDate).format(DATE_TIME_FORMAT);
  }
}
