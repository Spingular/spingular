import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITag, Tag } from 'app/shared/model/tag.model';
import { TagService } from './tag.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-tag-update',
  templateUrl: './tag-update.component.html'
})
export class TagUpdateComponent implements OnInit {
  isSaving: boolean;

  posts: IPost[];
  tag: ITag;
  tags: ITag[];
  appusers: IAppuser[];
  appuser: IAppuser;
  owner: any;
  isAdmin: boolean;

  currentAccount: any;
  isCreateDisabled = false;

  nameParamPost: any;
  valueParamPost: any;

  currentSearch: string;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any = 1;
  predicate: any = 'id';
  previousPage: any = 0;
  reverse: any = 'asc';
  id: any;

  editForm = this.fb.group({
    id: [],
    tagName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    posts: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tagService: TagService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected appuserService: AppuserService,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.postIdEquals != null) {
        this.nameParamPost = 'postId.equals';
        this.valueParamPost = params.postIdEquals;
      }
    });
  }

  // ngOnInit() {
  //   this.isSaving = false;
  //   this.activatedRoute.data.subscribe(({ tag }) => {
  //     this.updateForm(tag);
  //   });
  //   this.postService
  //     .query()
  //     .pipe(
  //       filter((mayBeOk: HttpResponse<IPost[]>) => mayBeOk.ok),
  //       map((response: HttpResponse<IPost[]>) => response.body)
  //     )
  //     .subscribe((res: IPost[]) => (this.posts = res), (res: HttpErrorResponse) => this.onError(res.message));
  // }

  ngOnInit() {
    this.isSaving = false;
    this.accountService.identity().subscribe(
      account => {
        this.currentAccount = account;
        this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
        const query = {};
        if (this.currentAccount.id != null) {
          query['userId.equals'] = this.currentAccount.id;
        }
        this.appuserService.query(query).subscribe((res: HttpResponse<IAppuser[]>) => {
          this.owner = res.body[0].id;
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.activatedRoute.data.subscribe(({ tag }) => {
      this.updateForm(tag);
    });
    if (this.valueParamPost != null) {
      const query = {};
      query['id.equals'] = this.valueParamPost;
      this.postService.query(query).subscribe(
        (res: HttpResponse<IPost[]>) => {
          this.posts = res.body;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    } else {
      this.postService.query().subscribe(
        (res: HttpResponse<IPost[]>) => {
          this.posts = res.body;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }

  loadAll() {
    if (this.currentSearch) {
      this.tagService
        .query({
          page: this.page - 1,
          'tagName.contains': this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ITag[]>) => this.paginateTags(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.tagService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ITag[]>) => this.paginateTags(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  addExistingTag2Post(tagId) {
    this.isSaving = true;
    if (tagId !== undefined) {
      const query = {};
      query['id.equals'] = tagId;
      this.tagService.query(query).subscribe(
        (res: HttpResponse<ITag[]>) => {
          this.tags = res.body;
          const query2 = {};
          if (this.valueParamPost != null) {
            query2['id.equals'] = this.valueParamPost;
          }
          this.postService.query(query2).subscribe(
            (res2: HttpResponse<IPost[]>) => {
              this.tags[0].posts.push(res2.body[0]);
              this.subscribeToSaveResponse(this.tagService.update(this.tags[0]));
            },
            (res2: HttpErrorResponse) => this.onError(res2.message)
          );
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/tag/new'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  search(query) {
    this.isCreateDisabled = true;
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/tag/new',
      {
        search: this.currentSearch,
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.currentSearch = '';
    this.router.navigate([
      '/tag/new',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  private paginateTags(data: IPost[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.tags = data;
    if (this.totalItems === 0) {
      this.tag.tagName = this.currentSearch;
    }
  }

  updateForm(tag: ITag) {
    this.editForm.patchValue({
      id: tag.id,
      tagName: tag.tagName,
      posts: tag.posts
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tag = this.createFromForm();
    if (tag.id !== undefined) {
      this.subscribeToSaveResponse(this.tagService.update(tag));
    } else {
      this.subscribeToSaveResponse(this.tagService.create(tag));
    }
  }

  private createFromForm(): ITag {
    return {
      ...new Tag(),
      id: this.editForm.get(['id']).value,
      tagName: this.editForm.get(['tagName']).value,
      posts: this.editForm.get(['posts']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITag>>) {
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

  trackPostById(index: number, item: IPost) {
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
}
