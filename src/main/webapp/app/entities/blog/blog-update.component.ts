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
import { IBlog, Blog } from 'app/shared/model/blog.model';
import { BlogService } from './blog.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-blog-update',
  templateUrl: './blog-update.component.html'
})
export class BlogUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];
  appuser: IAppuser;

  communities: ICommunity[];

  currentAccount: any;
  owner: any;
  isAdmin: boolean;
  creationDate: string;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    title: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    image: [],
    imageContentType: [],
    appuserId: [null, Validators.required],
    communityId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected blogService: BlogService,
    protected appuserService: AppuserService,
    protected communityService: CommunityService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ blog }) => {
      this.accountService.identity().subscribe(
        account => {
          this.currentAccount = account;
          this.owner = this.currentAccount.id;
          this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
          const query = {};
          if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
          }
          this.appuserService.query(query).subscribe((res: HttpResponse<IAppuser[]>) => {
            this.appusers = res.body;
            this.appuser = this.appusers[0];
            const query2 = {};
            if (this.currentAccount.id != null) {
              query2['appuserId.equals'] = this.appuser.id;
            }
            this.communityService
              .query(query2)
              .pipe(
                filter((mayBeOk: HttpResponse<ICommunity[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommunity[]>) => response.body)
              )
              .subscribe(
                (res2: ICommunity[]) => {
                  this.communities = res2;
                  this.updateForm(blog);
                },
                (res2: HttpErrorResponse) => this.onError(res2.message)
              );
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    });
  }

  updateForm(blog: IBlog) {
    this.editForm.patchValue({
      id: blog.id,
      creationDate: blog.creationDate != null ? blog.creationDate.format(DATE_TIME_FORMAT) : null,
      title: blog.title,
      image: blog.image,
      imageContentType: blog.imageContentType,
      appuserId: blog.appuserId,
      communityId: blog.communityId
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
    const blog = this.createFromForm();
    if (blog.id !== undefined) {
      this.subscribeToSaveResponse(this.blogService.update(blog));
    } else {
      this.subscribeToSaveResponse(this.blogService.create(blog));
    }
  }

  private createFromForm(): IBlog {
    return {
      ...new Blog(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      title: this.editForm.get(['title']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value,
      appuserId: this.editForm.get(['appuserId']).value,
      communityId: this.editForm.get(['communityId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlog>>) {
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

  trackCommunityById(index: number, item: ICommunity) {
    return item.id;
  }
}
