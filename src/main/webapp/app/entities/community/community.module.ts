import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { CommunityComponent } from './community.component';
import { CommunityDetailComponent } from './community-detail.component';
import { CommunityUpdateComponent } from './community-update.component';
import { CommunityDeletePopupComponent, CommunityDeleteDialogComponent } from './community-delete-dialog.component';
import { communityRoute, communityPopupRoute } from './community.route';

const ENTITY_STATES = [...communityRoute, ...communityPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CommunityComponent,
    CommunityDetailComponent,
    CommunityUpdateComponent,
    CommunityDeleteDialogComponent,
    CommunityDeletePopupComponent
  ],
  entryComponents: [CommunityDeleteDialogComponent]
})
export class SpingularCommunityModule {}
