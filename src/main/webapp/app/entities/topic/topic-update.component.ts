import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITopic, Topic } from 'app/shared/model/topic.model';
import { TopicService } from './topic.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-topic-update',
  templateUrl: './topic-update.component.html'
})
export class TopicUpdateComponent implements OnInit {
  isSaving: boolean;

  posts: IPost[];
  topic: ITopic;
  topics: ITopic[];
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
    topicName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    posts: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected topicService: TopicService,
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
    this.activatedRoute.data.subscribe(({ topic }) => {
      this.updateForm(topic);
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
      this.topicService
        .query({
          page: this.page - 1,
          'topicName.contains': this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ITopic[]>) => this.paginateTopics(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.topicService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ITopic[]>) => this.paginateTopics(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  addExistingTopic2Post(topicId) {
    this.isSaving = true;
    if (topicId !== undefined) {
      const query = {};
      query['id.equals'] = topicId;
      this.topicService.query(query).subscribe(
        (res: HttpResponse<ITopic[]>) => {
          this.topics = res.body;
          const query2 = {};
          if (this.valueParamPost != null) {
            query2['id.equals'] = this.valueParamPost;
          }
          this.postService.query(query2).subscribe(
            (res2: HttpResponse<IPost[]>) => {
              this.topics[0].posts.push(res2.body[0]);
              this.subscribeToSaveResponse(this.topicService.update(this.topics[0]));
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
    this.router.navigate(['/topic/new'], {
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
      '/topic/new',
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
      '/topic/new',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  private paginateTopics(data: IPost[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.topics = data;
    if (this.totalItems === 0) {
      this.topic.topicName = this.currentSearch;
    }
  }

  updateForm(topic: ITopic) {
    this.editForm.patchValue({
      id: topic.id,
      topicName: topic.topicName,
      posts: topic.posts
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const topic = this.createFromForm();
    if (topic.id !== undefined) {
      this.subscribeToSaveResponse(this.topicService.update(topic));
    } else {
      this.subscribeToSaveResponse(this.topicService.create(topic));
    }
  }

  private createFromForm(): ITopic {
    return {
      ...new Topic(),
      id: this.editForm.get(['id']).value,
      topicName: this.editForm.get(['topicName']).value,
      posts: this.editForm.get(['posts']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopic>>) {
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
