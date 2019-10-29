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
import { IPost, Post } from 'app/shared/model/post.model';
import { PostService } from './post.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IBlog } from 'app/shared/model/blog.model';
import { BlogService } from 'app/entities/blog/blog.service';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag/tag.service';
import { ITopic } from 'app/shared/model/topic.model';
import { TopicService } from 'app/entities/topic/topic.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-post-update',
  templateUrl: './post-update.component.html'
})
export class PostUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];
  appuser: IAppuser;

  blogs: IBlog[];

  tags: ITag[];

  topics: ITopic[];

  currentAccount: any;
  owner: any;
  isAdmin: boolean;
  creationDate: string;

  private _post: IPost;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    publicationDate: [],
    headline: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    leadtext: [null, [Validators.minLength(2), Validators.maxLength(1000)]],
    bodytext: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(65000)]],
    quote: [null, [Validators.minLength(2), Validators.maxLength(1000)]],
    conclusion: [null, [Validators.minLength(2), Validators.maxLength(2000)]],
    linkText: [null, [Validators.minLength(2), Validators.maxLength(1000)]],
    linkURL: [null, [Validators.minLength(2), Validators.maxLength(1000)]],
    image: [],
    imageContentType: [],
    appuserId: [null, Validators.required],
    blogId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected postService: PostService,
    protected appuserService: AppuserService,
    protected blogService: BlogService,
    protected tagService: TagService,
    protected topicService: TopicService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ post }) => {
      this.accountService.identity().subscribe(account => {
        this.currentAccount = account;
        this.owner = this.currentAccount.id;
        this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
        const query = {};
        if (this.currentAccount.id != null) {
          query['userId.equals'] = this.currentAccount.id;
        }
        this.appuserService.query(query).subscribe(
          (res: HttpResponse<IAppuser[]>) => {
            this.appusers = res.body;
            this.appuser = this.appusers[0];
            const query2 = {};
            if (this.currentAccount.id != null) {
              query2['appuserId.equals'] = this.appuser.id;
            }
            this.blogService
              .query(query2)
              .pipe(
                filter((mayBeOk: HttpResponse<IBlog[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBlog[]>) => response.body)
              )
              .subscribe(
                (res2: IBlog[]) => {
                  this.blogs = res2;
                  this.post = post;
                  this.creationDate = moment().format(DATE_TIME_FORMAT);
                  this.post.creationDate = moment(this.creationDate);
                  this.post.publicationDate = moment(this.creationDate);
                  this.updateForm(post);
                  this.updateForm(post);
                },
                (res2: HttpErrorResponse) => this.onError(res2.message)
              );
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      });
    });
    this.tagService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITag[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITag[]>) => response.body)
      )
      .subscribe((res: ITag[]) => (this.tags = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.topicService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITopic[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITopic[]>) => response.body)
      )
      .subscribe((res: ITopic[]) => (this.topics = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(post: IPost) {
    this.editForm.patchValue({
      id: post.id,
      creationDate: post.creationDate != null ? post.creationDate.format(DATE_TIME_FORMAT) : null,
      publicationDate: post.publicationDate != null ? post.publicationDate.format(DATE_TIME_FORMAT) : null,
      headline: post.headline,
      leadtext: post.leadtext,
      bodytext: post.bodytext,
      quote: post.quote,
      conclusion: post.conclusion,
      linkText: post.linkText,
      linkURL: post.linkURL,
      image: post.image,
      imageContentType: post.imageContentType,
      appuserId: post.appuserId,
      blogId: post.blogId
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
    const post = this.createFromForm();
    if (post.id !== undefined) {
      this.subscribeToSaveResponse(this.postService.update(post));
    } else {
      this.subscribeToSaveResponse(this.postService.create(post));
    }
  }

  private createFromForm(): IPost {
    return {
      ...new Post(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      publicationDate:
        this.editForm.get(['publicationDate']).value != null
          ? moment(this.editForm.get(['publicationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      headline: this.editForm.get(['headline']).value,
      leadtext: this.editForm.get(['leadtext']).value,
      bodytext: this.editForm.get(['bodytext']).value,
      quote: this.editForm.get(['quote']).value,
      conclusion: this.editForm.get(['conclusion']).value,
      linkText: this.editForm.get(['linkText']).value,
      linkURL: this.editForm.get(['linkURL']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value,
      appuserId: this.editForm.get(['appuserId']).value,
      blogId: this.editForm.get(['blogId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPost>>) {
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

  trackBlogById(index: number, item: IBlog) {
    return item.id;
  }

  trackTagById(index: number, item: ITag) {
    return item.id;
  }

  trackTopicById(index: number, item: ITopic) {
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

  get post() {
    return this._post;
  }

  set post(post: IPost) {
    this._post = post;
    this.creationDate = moment(post.creationDate).format(DATE_TIME_FORMAT);
  }
}
