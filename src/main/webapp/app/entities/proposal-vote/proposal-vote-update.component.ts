import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProposalVote, ProposalVote } from 'app/shared/model/proposal-vote.model';
import { ProposalVoteService } from './proposal-vote.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IProposal } from 'app/shared/model/proposal.model';
import { ProposalService } from 'app/entities/proposal/proposal.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-proposal-vote-update',
  templateUrl: './proposal-vote-update.component.html'
})
export class ProposalVoteUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];
  appuser: IAppuser;

  proposals: IProposal[];
  proposal: IProposal;

  proposalVotes: IProposalVote[];
  proposalVotes2: IProposalVote[];

  error: any;
  success: any;
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

  currentAccount: any;

  nameParamFollows: any;
  valueParamFollows: any;
  userQuery: boolean;

  totalProposalVotes: number;
  appuserVotes: number;
  userAvailableProposalVotes: number;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    votePoints: [null, [Validators.required]],
    appuserId: [],
    proposalId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected proposalVoteService: ProposalVoteService,
    protected appuserService: AppuserService,
    protected proposalService: ProposalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.proposalIdEquals != null) {
        this.nameParamFollows = 'proposalId.equals';
        this.valueParamFollows = params.proposalIdEquals;
        this.userQuery = true;
      }
    });
  }

  // ngOnInit() {
  //   this.isSaving = false;
  //   this.activatedRoute.data.subscribe(({ proposalVote }) => {
  //     this.updateForm(proposalVote);
  //   });
  //   this.appuserService
  //     .query()
  //     .pipe(
  //       filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
  //       map((response: HttpResponse<IAppuser[]>) => response.body)
  //     )
  //     .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  //   this.proposalService
  //     .query()
  //     .pipe(
  //       filter((mayBeOk: HttpResponse<IProposal[]>) => mayBeOk.ok),
  //       map((response: HttpResponse<IProposal[]>) => response.body)
  //     )
  //     .subscribe((res: IProposal[]) => (this.proposals = res), (res: HttpErrorResponse) => this.onError(res.message));
  // }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proposalVote }) => {
      this.updateForm(proposalVote);
    });
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
      this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
      const query = {};
      if (this.currentAccount.id != null) {
        query['userId.equals'] = this.currentAccount.id;
      }
      this.appuserService.query(query).subscribe(
        (res: HttpResponse<IAppuser[]>) => {
          this.owner = res.body[0].id;
          this.appuser = res.body[0];
          this.appusers = res.body;
          const query4 = {};
          query4['id.equals'] = this.valueParamFollows;
          this.proposalService.query(query4).subscribe(
            (res4: HttpResponse<IProposal[]>) => {
              this.proposal = res4.body[0];
              const query2 = {};
              query2['proposalId.equals'] = this.valueParamFollows;
              this.proposalVoteService.query(query2).subscribe(
                (res2: HttpResponse<IProposalVote[]>) => {
                  this.proposalVotes = [];
                  this.proposalVotes = res2.body;
                  this.totalProposalVotes = 0;
                  this.appuserVotes = 0;
                  this.proposalVotes.forEach(proposalVote => {
                    this.totalProposalVotes = this.totalProposalVotes + proposalVote.votePoints;
                    if (proposalVote.appuserId === this.owner) {
                      this.appuserVotes = this.appuserVotes + proposalVote.votePoints;
                    }
                    const query3 = {};
                    //                    query3['proposalUserId.equals'] = this.currentAccount.id;profileId
                    query3['appuserId.equals'] = this.owner;
                    this.proposalVoteService.query(query3).subscribe(
                      (res3: HttpResponse<IProposalVote[]>) => {
                        this.proposalVotes2 = [];
                        this.proposalVotes2 = res3.body;
                        this.userAvailableProposalVotes = this.appuser.assignedVotesPoints;
                        this.proposalVotes2.forEach(voteProposal2 => {
                          this.userAvailableProposalVotes = this.userAvailableProposalVotes - voteProposal2.votePoints;
                        });
                        this.activatedRoute.data.subscribe(({ voteProposal }) => {
                          this.updateForm(voteProposal);
                        });
                      },
                      (res3: HttpErrorResponse) => this.onError(res3.message)
                    );
                  });
                },
                (res2: HttpErrorResponse) => this.onError(res2.message)
              );
            },
            (res4: HttpErrorResponse) => this.onError(res4.message)
          );
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    });
    this.proposalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProposal[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProposal[]>) => response.body)
      )
      .subscribe((res: IProposal[]) => (this.proposals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(proposalVote: IProposalVote) {
    this.editForm.patchValue({
      id: proposalVote.id,
      creationDate: proposalVote.creationDate != null ? proposalVote.creationDate.format(DATE_TIME_FORMAT) : null,
      votePoints: proposalVote.votePoints,
      appuserId: proposalVote.appuserId,
      proposalId: proposalVote.proposalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const proposalVote = this.createFromForm();
    if (proposalVote.id !== undefined) {
      this.subscribeToSaveResponse(this.proposalVoteService.update(proposalVote));
    } else {
      this.subscribeToSaveResponse(this.proposalVoteService.create(proposalVote));
    }
  }

  private createFromForm(): IProposalVote {
    return {
      ...new ProposalVote(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      votePoints: this.editForm.get(['votePoints']).value,
      appuserId: this.editForm.get(['appuserId']).value,
      proposalId: this.editForm.get(['proposalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProposalVote>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAppuserById(index: number, item: IAppuser) {
    return item.id;
  }

  trackProposalById(index: number, item: IProposal) {
    return item.id;
  }
}
