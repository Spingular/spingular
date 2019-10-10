import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { ProposalVoteComponent } from './proposal-vote.component';
import { ProposalVoteDetailComponent } from './proposal-vote-detail.component';
import { ProposalVoteUpdateComponent } from './proposal-vote-update.component';
import { ProposalVoteDeletePopupComponent, ProposalVoteDeleteDialogComponent } from './proposal-vote-delete-dialog.component';
import { proposalVoteRoute, proposalVotePopupRoute } from './proposal-vote.route';

const ENTITY_STATES = [...proposalVoteRoute, ...proposalVotePopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProposalVoteComponent,
    ProposalVoteDetailComponent,
    ProposalVoteUpdateComponent,
    ProposalVoteDeleteDialogComponent,
    ProposalVoteDeletePopupComponent
  ],
  entryComponents: [ProposalVoteDeleteDialogComponent]
})
export class SpingularProposalVoteModule {}
