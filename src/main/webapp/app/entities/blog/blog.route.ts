import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Blog } from 'app/shared/model/blog.model';
import { BlogService } from './blog.service';
import { BlogComponent } from './blog.component';
import { BlogDetailComponent } from './blog-detail.component';
import { BlogUpdateComponent } from './blog-update.component';
import { BlogDeletePopupComponent } from './blog-delete-dialog.component';
import { IBlog } from 'app/shared/model/blog.model';

@Injectable({ providedIn: 'root' })
export class BlogResolve implements Resolve<IBlog> {
  constructor(private service: BlogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBlog> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Blog>) => response.ok),
        map((blog: HttpResponse<Blog>) => blog.body)
      );
    }
    return of(new Blog());
  }
}

export const blogRoute: Routes = [
  {
    path: '',
    component: BlogComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'spingularApp.blog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BlogDetailComponent,
    resolve: {
      blog: BlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.blog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BlogUpdateComponent,
    resolve: {
      blog: BlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.blog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BlogUpdateComponent,
    resolve: {
      blog: BlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.blog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const blogPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BlogDeletePopupComponent,
    resolve: {
      blog: BlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.blog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
