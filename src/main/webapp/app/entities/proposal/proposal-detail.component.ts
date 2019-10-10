import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProposal } from 'app/shared/model/proposal.model';

@Component({
  selector: 'jhi-proposal-detail',
  templateUrl: './proposal-detail.component.html'
})
export class ProposalDetailComponent implements OnInit {
  proposal: IProposal;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proposal }) => {
      this.proposal = proposal;
    });
  }

  previousState() {
    window.history.back();
  }
}
