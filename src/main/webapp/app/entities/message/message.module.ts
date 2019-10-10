import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { MessageComponent } from './message.component';
import { MessageDetailComponent } from './message-detail.component';
import { MessageUpdateComponent } from './message-update.component';
import { MessageDeletePopupComponent, MessageDeleteDialogComponent } from './message-delete-dialog.component';
import { messageRoute, messagePopupRoute } from './message.route';

const ENTITY_STATES = [...messageRoute, ...messagePopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MessageComponent,
    MessageDetailComponent,
    MessageUpdateComponent,
    MessageDeleteDialogComponent,
    MessageDeletePopupComponent
  ],
  entryComponents: [MessageDeleteDialogComponent]
})
export class SpingularMessageModule {}
