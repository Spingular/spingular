import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProposalVote } from 'app/shared/model/proposal-vote.model';

@Component({
  selector: 'jhi-proposal-vote-detail',
  templateUrl: './proposal-vote-detail.component.html'
})
export class ProposalVoteDetailComponent implements OnInit {
  proposalVote: IProposalVote;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proposalVote }) => {
      this.proposalVote = proposalVote;
    });
  }

  previousState() {
    window.history.back();
  }
}
