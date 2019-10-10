import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Message } from 'app/shared/model/message.model';
import { MessageService } from './message.service';
import { MessageComponent } from './message.component';
import { MessageDetailComponent } from './message-detail.component';
import { MessageUpdateComponent } from './message-update.component';
import { MessageDeletePopupComponent } from './message-delete-dialog.component';
import { IMessage } from 'app/shared/model/message.model';

@Injectable({ providedIn: 'root' })
export class MessageResolve implements Resolve<IMessage> {
  constructor(private service: MessageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMessage> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Message>) => response.ok),
        map((message: HttpResponse<Message>) => message.body)
      );
    }
    return of(new Message());
  }
}

export const messageRoute: Routes = [
  {
    path: '',
    component: MessageComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'spingularApp.message.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MessageDetailComponent,
    resolve: {
      message: MessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.message.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MessageUpdateComponent,
    resolve: {
      message: MessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.message.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MessageUpdateComponent,
    resolve: {
      message: MessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.message.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const messagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MessageDeletePopupComponent,
    resolve: {
      message: MessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.message.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
