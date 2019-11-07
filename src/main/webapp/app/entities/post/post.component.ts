import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPost } from 'app/shared/model/post.model';
import { PostService } from './post.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

import { AccountService } from 'app/core/auth/account.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-post',
  templateUrl: './post.component.html'
})
export class PostComponent implements OnInit, OnDestroy {
  currentAccount: any;
  posts: IPost[];
  appusers: IAppuser[];
  appuser: IAppuser;

  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  currentSearch: string;
  owner: any;
  isAdmin: boolean;

  arrayAux = [];
  arrayIds = [];

  constructor(
    protected postService: PostService,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected jhiAlertService: JhiAlertService,
    protected appuserService: AppuserService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      const query = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      };
      query['headline.contains'] = this.currentSearch;
      this.postService.query(query).subscribe(
        (res: HttpResponse<IPost[]>) => {
          // this.posts = res.body;
          this.paginatePosts(res.body, res.headers);
          const query2 = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
          };
          query2['bodytext.contains'] = this.currentSearch;
          this.postService.query(query2).subscribe(
            (res2: HttpResponse<IPost[]>) => {
              this.paginatePosts(this.filterArray(this.posts, res2.body), res2.headers);
              const query3 = {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
              };
              query3['conclusion.contains'] = this.currentSearch;
              this.postService.query(query3).subscribe(
                (res3: HttpResponse<IPost[]>) => {
                  this.paginatePosts(this.filterArray(this.posts, res3.body), res3.headers);
                },
                (res3: HttpErrorResponse) => this.onError(res3.message)
              );
            },
            (res2: HttpErrorResponse) => this.onError(res2.message)
          );
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      return;
    }
    this.postService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPost[]>) => this.paginatePosts(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  private filterArray(arr1, arr2) {
    const newArray = arr1.filter(arr => arr2.findIndex(arrAux => arrAux.id === arr.id) === -1);
    return [...arr2, ...newArray];
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/post'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.currentSearch = '';
    this.router.navigate([
      '/post',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/post',
      {
        search: this.currentSearch,
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
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
          this.owner = this.appuser.id;
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.registerChangeInPosts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPost) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPosts() {
    this.eventSubscriber = this.eventManager.subscribe('postListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  myPosts() {
    const query = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    query['appuserId.equals'] = this.currentAccount.id;
    this.postService
      .query(query)
      .subscribe(
        (res: HttpResponse<IPost[]>) => this.paginatePosts(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  protected paginatePosts(data: IPost[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.posts = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
