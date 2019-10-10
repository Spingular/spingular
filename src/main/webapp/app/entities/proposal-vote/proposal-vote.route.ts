import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProposalVote } from 'app/shared/model/proposal-vote.model';
import { ProposalVoteService } from './proposal-vote.service';
import { ProposalVoteComponent } from './proposal-vote.component';
import { ProposalVoteDetailComponent } from './proposal-vote-detail.component';
import { ProposalVoteUpdateComponent } from './proposal-vote-update.component';
import { ProposalVoteDeletePopupComponent } from './proposal-vote-delete-dialog.component';
import { IProposalVote } from 'app/shared/model/proposal-vote.model';

@Injectable({ providedIn: 'root' })
export class ProposalVoteResolve implements Resolve<IProposalVote> {
  constructor(private service: ProposalVoteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProposalVote> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProposalVote>) => response.ok),
        map((proposalVote: HttpResponse<ProposalVote>) => proposalVote.body)
      );
    }
    return of(new ProposalVote());
  }
}

export const proposalVoteRoute: Routes = [
  {
    path: '',
    component: ProposalVoteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'spingularApp.proposalVote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProposalVoteDetailComponent,
    resolve: {
      proposalVote: ProposalVoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.proposalVote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProposalVoteUpdateComponent,
    resolve: {
      proposalVote: ProposalVoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.proposalVote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProposalVoteUpdateComponent,
    resolve: {
      proposalVote: ProposalVoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.proposalVote.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const proposalVotePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProposalVoteDeletePopupComponent,
    resolve: {
      proposalVote: ProposalVoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.proposalVote.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
