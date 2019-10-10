import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Appphoto } from 'app/shared/model/appphoto.model';
import { AppphotoService } from './appphoto.service';
import { AppphotoComponent } from './appphoto.component';
import { AppphotoDetailComponent } from './appphoto-detail.component';
import { AppphotoUpdateComponent } from './appphoto-update.component';
import { AppphotoDeletePopupComponent } from './appphoto-delete-dialog.component';
import { IAppphoto } from 'app/shared/model/appphoto.model';

@Injectable({ providedIn: 'root' })
export class AppphotoResolve implements Resolve<IAppphoto> {
  constructor(private service: AppphotoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAppphoto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Appphoto>) => response.ok),
        map((appphoto: HttpResponse<Appphoto>) => appphoto.body)
      );
    }
    return of(new Appphoto());
  }
}

export const appphotoRoute: Routes = [
  {
    path: '',
    component: AppphotoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'spingularApp.appphoto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AppphotoDetailComponent,
    resolve: {
      appphoto: AppphotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appphoto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AppphotoUpdateComponent,
    resolve: {
      appphoto: AppphotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appphoto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AppphotoUpdateComponent,
    resolve: {
      appphoto: AppphotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appphoto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const appphotoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AppphotoDeletePopupComponent,
    resolve: {
      appphoto: AppphotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.appphoto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
