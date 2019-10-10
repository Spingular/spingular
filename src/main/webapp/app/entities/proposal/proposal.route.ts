import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Proposal } from 'app/shared/model/proposal.model';
import { ProposalService } from './proposal.service';
import { ProposalComponent } from './proposal.component';
import { ProposalDetailComponent } from './proposal-detail.component';
import { ProposalUpdateComponent } from './proposal-update.component';
import { ProposalDeletePopupComponent } from './proposal-delete-dialog.component';
import { IProposal } from 'app/shared/model/proposal.model';

@Injectable({ providedIn: 'root' })
export class ProposalResolve implements Resolve<IProposal> {
  constructor(private service: ProposalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProposal> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Proposal>) => response.ok),
        map((proposal: HttpResponse<Proposal>) => proposal.body)
      );
    }
    return of(new Proposal());
  }
}

export const proposalRoute: Routes = [
  {
    path: '',
    component: ProposalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'spingularApp.proposal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProposalDetailComponent,
    resolve: {
      proposal: ProposalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.proposal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProposalUpdateComponent,
    resolve: {
      proposal: ProposalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.proposal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProposalUpdateComponent,
    resolve: {
      proposal: ProposalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.proposal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const proposalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProposalDeletePopupComponent,
    resolve: {
      proposal: ProposalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'spingularApp.proposal.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
