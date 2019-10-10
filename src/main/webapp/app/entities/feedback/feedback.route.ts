import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Feedback } from 'app/shared/model/feedback.model';
import { FeedbackService } from './feedback.service';
import { FeedbackComponent } from './feedback.component';
import { FeedbackDetailComponent } from './feedback-detail.component';
import { FeedbackUpdateComponent } from './feedback-update.component';
import { FeedbackDeletePopupComponent } from './feedback-delete-dialog.component';
import { IFeedback } from 'app/shared/model/feedback.model';

@Injectable({ providedIn: 'root' })
export class FeedbackResolve implements Resolve<IFeedback> {
  constructor(private service: FeedbackService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFeedback> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Feedback>) => response.ok),
        map((feedback: HttpResponse<Feedback>) => feedback.body)
      );
    }
    return of(new Feedback());
  }
}

export const feedbackRoute: Routes = [
  {
    path: '',
    component: FeedbackComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'spingularApp.feedback.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FeedbackDetailComponent,
    resolve: {
      feedback: FeedbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.feedback.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FeedbackUpdateComponent,
    resolve: {
      feedback: FeedbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.feedback.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FeedbackUpdateComponent,
    resolve: {
      feedback: FeedbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.feedback.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const feedbackPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FeedbackDeletePopupComponent,
    resolve: {
      feedback: FeedbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.feedback.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
