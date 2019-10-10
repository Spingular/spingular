import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Appuser } from 'app/shared/model/appuser.model';
import { AppuserService } from './appuser.service';
import { AppuserComponent } from './appuser.component';
import { AppuserDetailComponent } from './appuser-detail.component';
import { AppuserUpdateComponent } from './appuser-update.component';
import { AppuserDeletePopupComponent } from './appuser-delete-dialog.component';
import { IAppuser } from 'app/shared/model/appuser.model';

@Injectable({ providedIn: 'root' })
export class AppuserResolve implements Resolve<IAppuser> {
  constructor(private service: AppuserService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAppuser> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Appuser>) => response.ok),
        map((appuser: HttpResponse<Appuser>) => appuser.body)
      );
    }
    return of(new Appuser());
  }
}

export const appuserRoute: Routes = [
  {
    path: '',
    component: AppuserComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'spingularApp.appuser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AppuserDetailComponent,
    resolve: {
      appuser: AppuserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appuser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AppuserUpdateComponent,
    resolve: {
      appuser: AppuserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appuser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AppuserUpdateComponent,
    resolve: {
      appuser: AppuserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appuser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const appuserPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AppuserDeletePopupComponent,
    resolve: {
      appuser: AppuserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appuser.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
