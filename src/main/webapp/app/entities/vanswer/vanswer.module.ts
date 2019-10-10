import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { VanswerComponent } from './vanswer.component';
import { VanswerDetailComponent } from './vanswer-detail.component';
import { VanswerUpdateComponent } from './vanswer-update.component';
import { VanswerDeletePopupComponent, VanswerDeleteDialogComponent } from './vanswer-delete-dialog.component';
import { vanswerRoute, vanswerPopupRoute } from './vanswer.route';

const ENTITY_STATES = [...vanswerRoute, ...vanswerPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VanswerComponent,
    VanswerDetailComponent,
    VanswerUpdateComponent,
    VanswerDeleteDialogComponent,
    VanswerDeletePopupComponent
  ],
  entryComponents: [VanswerDeleteDialogComponent]
})
export class SpingularVanswerModule {}
