import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IBlog } from 'app/shared/model/blog.model';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';

@Component({
  selector: 'jhi-blog-detail',
  templateUrl: './blog-detail.component.html'
})
export class BlogDetailComponent implements OnInit {
  blog: IBlog;

  post: any;
  posts: IPost[];

  error: any;
  success: any;
  // eventSubscriber: Subscription;
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
    protected activatedRoute: ActivatedRoute,
    protected postService: PostService,
    protected parseLinks: JhiParseLinks,
    protected router: Router,
    protected jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ blog }) => {
      this.blog = blog;
      const query = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      };
      if (this.blog != null) {
        query['blogId.equals'] = this.blog.id;
      }
      this.postService.query(query).subscribe(
        (res: HttpResponse<IPost[]>) => {
          this.posts = res.body;
          this.paginatePosts(res.body, res.headers);
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/blog/', this.id, '/view'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    // this.loadAll();
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  protected paginatePosts(data: IPost[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.posts = data;
  }

  previousState() {
    window.history.back();
  }
}
