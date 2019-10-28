import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs';

import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from 'app/entities/comment/comment.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IAppprofile } from 'app/shared/model/appprofile.model';
import { AppprofileService } from 'app/entities/appprofile/appprofile.service';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag/tag.service';
import { ITopic } from 'app/shared/model/topic.model';
import { TopicService } from 'app/entities/topic/topic.service';

import { AccountService } from 'app/core/auth/account.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-post-detail',
  templateUrl: './post-detail.component.html'
})
export class PostDetailComponent implements OnInit {
  private _comment: IComment;
  isSaving: boolean;

  post: IPost;
  posts: IPost[];
  comments: IComment[];
  tags: ITag[];
  topics: ITopic[];

  appprofile: IAppprofile;
  appprofiles: IAppprofile[];

  appuser: IAppuser;
  appusers: IAppuser[];

  currentAccount: any;
  creationDate: string;
  owner: any = 0;
  isAdmin: boolean;
  postUserFirstName: string;
  postUserLastName: string;

  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any = 1;
  predicate: any = 'id';
  previousPage: any = 0;
  reverse: any = 'asc';
  id: any;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected commentService: CommentService,
    protected postService: PostService,
    protected appprofileService: AppprofileService,
    protected tagService: TagService,
    protected topicService: TopicService,
    protected accountService: AccountService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      //  this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams ? data.pagingParams.page : 0;
      this.reverse = data.pagingParams ? data.pagingParams.ascending : 'asc';
      this.predicate = data.pagingParams ? data.pagingParams.predicate : 'id';
    });
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ post }) => {
      this.post = post;
    });
    this.accountService.identity().subscribe(account => {
      if (account != null) {
        this.currentAccount = account;
        this.owner = this.currentAccount.id;
        this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
        this.loadAll();
        this.comment = new Object();
        this.comment.commentText = '';
        this.registerChangeInComments();
      }
    });
  }

  saveComment() {
    this.isSaving = true;
    this.comment.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    if (this.comment.id !== undefined) {
      this.subscribeToSaveResponse(this.commentService.update(this.comment));
    } else {
      this.comment.appuserId = this.appuser.id;
      this.comment.isOffensive = false;
      this.comment.postId = this.post.id;
      this.subscribeToSaveResponse(this.commentService.create(this.comment));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
    result.subscribe((res: HttpResponse<IComment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.loadAll();
    this.comment.commentText = '';
    //        this.reload();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPostById(index: number, item: IPost) {
    return item.id;
  }

  trackUserById(index: number, item: IAppuser) {
    return item.id;
  }

  get comment() {
    return this._comment;
  }

  set comment(comment: IComment) {
    this._comment = comment;
    this.creationDate = moment(comment.creationDate).format(DATE_TIME_FORMAT);
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }

  reload() {
    window.location.reload();
  }

  loadPage(page) {
    this.previousPage = page;
    this.page = page;
    this.loadAll();
  }

  loadAll() {
    const query = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    query['postId.equals'] = this.post.id;
    this.commentService.query(query).subscribe(
      (res: HttpResponse<IComment[]>) => {
        this.paginateComments(res.body, res.headers);
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    const query2 = {};
    if (this.post.appuserId != null) {
      query2['appuserId.equals'] = this.post.appuserId;
    }
    this.appprofileService.query(query2).subscribe(
      (res: HttpResponse<IAppprofile[]>) => {
        this.appprofiles = res.body;
        this.appprofile = res.body[0];
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    const query3 = {};
    if (this.currentAccount.id != null) {
      query3['userId.equals'] = this.currentAccount.id;
    }
    this.appuserService.query(query3).subscribe(
      (res: HttpResponse<IAppuser[]>) => {
        this.appusers = res.body;
        this.appuser = res.body[0];
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  removePostTag(tagId, postId) {
    this.post.tags.forEach(tag => {
      if (tag.id === tagId) {
        this.post.tags.splice(this.post.tags.indexOf(tag), 1);
        this.subscribeToSaveResponse2(this.tagService.update(tag));
      }
    });
  }

  removePostTopic(topicId, postId) {
    this.post.topics.forEach(topic => {
      if (topic.id === topicId) {
        this.post.topics.splice(this.post.topics.indexOf(topic), 1);
        this.subscribeToSaveResponse3(this.topicService.update(topic));
      }
    });
  }

  private subscribeToSaveResponse2(result: Observable<HttpResponse<ITag>>) {
    result.subscribe((res: HttpResponse<ITag>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private subscribeToSaveResponse3(result: Observable<HttpResponse<ITopic>>) {
    result.subscribe((res: HttpResponse<ITopic>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess2() {
    this.isSaving = false;
  }

  transition() {
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/comment',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  trackId(index: number, item: IComment) {
    return item.id;
  }

  registerChangeInComments() {
    this.eventSubscriber = this.eventManager.subscribe('commentListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private paginateComments(data: IComment[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.comments = data;
  }
}
