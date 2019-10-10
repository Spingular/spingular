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

@Component({
  selector: 'jhi-proposal-vote-update',
  templateUrl: './proposal-vote-update.component.html'
})
export class ProposalVoteUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  proposals: IProposal[];

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
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proposalVote }) => {
      this.updateForm(proposalVote);
    });
    this.appuserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppuser[]>) => response.body)
      )
      .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
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
