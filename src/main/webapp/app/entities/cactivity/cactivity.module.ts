import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { CactivityComponent } from './cactivity.component';
import { CactivityDetailComponent } from './cactivity-detail.component';
import { CactivityUpdateComponent } from './cactivity-update.component';
import { CactivityDeletePopupComponent, CactivityDeleteDialogComponent } from './cactivity-delete-dialog.component';
import { cactivityRoute, cactivityPopupRoute } from './cactivity.route';

const ENTITY_STATES = [...cactivityRoute, ...cactivityPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CactivityComponent,
    CactivityDetailComponent,
    CactivityUpdateComponent,
    CactivityDeleteDialogComponent,
    CactivityDeletePopupComponent
  ],
  entryComponents: [CactivityDeleteDialogComponent]
})
export class SpingularCactivityModule {}
