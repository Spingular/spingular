import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Appprofile } from 'app/shared/model/appprofile.model';
import { AppprofileService } from './appprofile.service';
import { AppprofileComponent } from './appprofile.component';
import { AppprofileDetailComponent } from './appprofile-detail.component';
import { AppprofileUpdateComponent } from './appprofile-update.component';
import { AppprofileDeletePopupComponent } from './appprofile-delete-dialog.component';
import { IAppprofile } from 'app/shared/model/appprofile.model';

@Injectable({ providedIn: 'root' })
export class AppprofileResolve implements Resolve<IAppprofile> {
  constructor(private service: AppprofileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAppprofile> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Appprofile>) => response.ok),
        map((appprofile: HttpResponse<Appprofile>) => appprofile.body)
      );
    }
    return of(new Appprofile());
  }
}

export const appprofileRoute: Routes = [
  {
    path: '',
    component: AppprofileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'spingularApp.appprofile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AppprofileDetailComponent,
    resolve: {
      appprofile: AppprofileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appprofile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AppprofileUpdateComponent,
    resolve: {
      appprofile: AppprofileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appprofile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AppprofileUpdateComponent,
    resolve: {
      appprofile: AppprofileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appprofile.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const appprofilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AppprofileDeletePopupComponent,
    resolve: {
      appprofile: AppprofileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appprofile.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
