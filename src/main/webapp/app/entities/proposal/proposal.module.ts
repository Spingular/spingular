import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { ProposalComponent } from './proposal.component';
import { ProposalDetailComponent } from './proposal-detail.component';
import { ProposalUpdateComponent } from './proposal-update.component';
import { ProposalDeletePopupComponent, ProposalDeleteDialogComponent } from './proposal-delete-dialog.component';
import { proposalRoute, proposalPopupRoute } from './proposal.route';

const ENTITY_STATES = [...proposalRoute, ...proposalPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProposalComponent,
    ProposalDetailComponent,
    ProposalUpdateComponent,
    ProposalDeleteDialogComponent,
    ProposalDeletePopupComponent
  ],
  entryComponents: [ProposalDeleteDialogComponent]
})
export class SpingularProposalModule {}
