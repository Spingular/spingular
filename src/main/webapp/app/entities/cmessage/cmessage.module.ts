import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { CmessageComponent } from './cmessage.component';
import { CmessageDetailComponent } from './cmessage-detail.component';
import { CmessageUpdateComponent } from './cmessage-update.component';
import { CmessageDeletePopupComponent, CmessageDeleteDialogComponent } from './cmessage-delete-dialog.component';
import { cmessageRoute, cmessagePopupRoute } from './cmessage.route';

const ENTITY_STATES = [...cmessageRoute, ...cmessagePopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CmessageComponent,
    CmessageDetailComponent,
    CmessageUpdateComponent,
    CmessageDeleteDialogComponent,
    CmessageDeletePopupComponent
  ],
  entryComponents: [CmessageDeleteDialogComponent]
})
export class SpingularCmessageModule {}
