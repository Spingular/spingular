import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { BlockuserComponent } from './blockuser.component';
import { BlockuserDetailComponent } from './blockuser-detail.component';
import { BlockuserUpdateComponent } from './blockuser-update.component';
import { BlockuserDeletePopupComponent, BlockuserDeleteDialogComponent } from './blockuser-delete-dialog.component';
import { blockuserRoute, blockuserPopupRoute } from './blockuser.route';

const ENTITY_STATES = [...blockuserRoute, ...blockuserPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BlockuserComponent,
    BlockuserDetailComponent,
    BlockuserUpdateComponent,
    BlockuserDeleteDialogComponent,
    BlockuserDeletePopupComponent
  ],
  entryComponents: [BlockuserDeleteDialogComponent]
})
export class SpingularBlockuserModule {}
