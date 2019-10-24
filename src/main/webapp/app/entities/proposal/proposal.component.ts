import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IProposal } from 'app/shared/model/proposal.model';
import { ProposalService } from './proposal.service';
import { IProposalVote } from 'app/shared/model/proposal-vote.model';
import { ProposalVoteService } from 'app/entities/proposal-vote/proposal-vote.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-proposal',
  templateUrl: './proposal.component.html'
})
export class ProposalComponent implements OnInit, OnDestroy {
  currentAccount: any;
  proposals: IProposal[];
  proposalVotes: IProposalVote[];
  appusers: IAppuser[];
  appuser: IAppuser;

  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  owner: any;
  isAdmin: boolean;

  totalVotes: number;
  totalUserVotes: number;

  constructor(
    protected proposalService: ProposalService,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected proposalVoteService: ProposalVoteService,
    protected appuserService: AppuserService,
    protected jhiAlertService: JhiAlertService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.proposalService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProposal[]>) => {
          this.proposals = res.body;
          this.proposals.forEach(proposal => {
            const query2 = {};
            query2['proposalId.equals'] = proposal.id;
            this.proposalVoteService.query(query2).subscribe(
              (res2: HttpResponse<IProposalVote[]>) => {
                this.proposalVotes = [];
                this.proposalVotes = res2.body;
                const totalVotes = {};
                const totalUserVotes = {};
                this.proposalVotes.forEach(proposalVote => {
                  totalVotes[proposalVote.proposalId] = totalVotes[proposalVote.proposalId]
                    ? totalVotes[proposalVote.proposalId] + proposalVote.votePoints
                    : proposalVote.votePoints;
                  if (proposalVote.appuserId === this.currentAccount.id) {
                    totalUserVotes[proposalVote.proposalId] = totalUserVotes[proposalVote.proposalId]
                      ? totalUserVotes[proposalVote.proposalId] + proposalVote.votePoints
                      : proposalVote.votePoints;
                  }
                });
                proposal.totalVotes = totalVotes[proposal.id];
                proposal.totalUserVotes = totalUserVotes[proposal.id];
              },
              (res2: HttpErrorResponse) => this.onError(res2.message)
            );
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  myProposals() {
    const query = {};
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.owner;
      query['queryParams'] = 1;
    }
    this.proposalService.query(query).subscribe(
      (res: HttpResponse<IProposal[]>) => {
        this.paginateProposals(res.body, res.headers);
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/proposal'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/proposal',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(
      account => {
        this.currentAccount = account;
        this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
        const query = {};
        if (this.currentAccount.id != null) {
          query['id.equals'] = this.currentAccount.id;
          query['queryParams'] = 1;
        }
        this.appuserService.query(query).subscribe((res: HttpResponse<IAppuser[]>) => {
          this.owner = res.body[0].id;
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.registerChangeInProposals();
  }

  study() {
    const query = {};
    query['proposalType.equals'] = 'STUDY';
    query['queryParams'] = 1;
    this.byTypeQuery(query);
  }
  approved() {
    const query = {};
    query['proposalType.equals'] = 'APPROVED';
    query['queryParams'] = 1;
    this.byTypeQuery(query);
  }

  development() {
    const query = {};
    query['proposalType.equals'] = 'DEVELOPMENT';
    query['queryParams'] = 1;
    this.byTypeQuery(query);
  }

  production() {
    const query = {};
    query['proposalType.equals'] = 'PRODUCTION';
    query['queryParams'] = 1;
    this.byTypeQuery(query);
  }

  byTypeQuery(query) {
    this.proposalService.query(query).subscribe(
      (res: HttpResponse<IProposal[]>) => {
        this.paginateProposals(res.body, res.headers);
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProposal) {
    return item.id;
  }

  registerChangeInProposals() {
    this.eventSubscriber = this.eventManager.subscribe('proposalListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProposals(data: IProposal[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.proposals = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
