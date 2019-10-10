import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cmessage } from 'app/shared/model/cmessage.model';
import { CmessageService } from './cmessage.service';
import { CmessageComponent } from './cmessage.component';
import { CmessageDetailComponent } from './cmessage-detail.component';
import { CmessageUpdateComponent } from './cmessage-update.component';
import { CmessageDeletePopupComponent } from './cmessage-delete-dialog.component';
import { ICmessage } from 'app/shared/model/cmessage.model';

@Injectable({ providedIn: 'root' })
export class CmessageResolve implements Resolve<ICmessage> {
  constructor(private service: CmessageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICmessage> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cmessage>) => response.ok),
        map((cmessage: HttpResponse<Cmessage>) => cmessage.body)
      );
    }
    return of(new Cmessage());
  }
}

export const cmessageRoute: Routes = [
  {
    path: '',
    component: CmessageComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'spingularApp.cmessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CmessageDetailComponent,
    resolve: {
      cmessage: CmessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.cmessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CmessageUpdateComponent,
    resolve: {
      cmessage: CmessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.cmessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CmessageUpdateComponent,
    resolve: {
      cmessage: CmessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.cmessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cmessagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CmessageDeletePopupComponent,
    resolve: {
      cmessage: CmessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.cmessage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
