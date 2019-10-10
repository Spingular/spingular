import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { FrontpageconfigComponent } from './frontpageconfig.component';
import { FrontpageconfigDetailComponent } from './frontpageconfig-detail.component';
import { FrontpageconfigUpdateComponent } from './frontpageconfig-update.component';
import { FrontpageconfigDeletePopupComponent, FrontpageconfigDeleteDialogComponent } from './frontpageconfig-delete-dialog.component';
import { frontpageconfigRoute, frontpageconfigPopupRoute } from './frontpageconfig.route';

const ENTITY_STATES = [...frontpageconfigRoute, ...frontpageconfigPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FrontpageconfigComponent,
    FrontpageconfigDetailComponent,
    FrontpageconfigUpdateComponent,
    FrontpageconfigDeleteDialogComponent,
    FrontpageconfigDeletePopupComponent
  ],
  entryComponents: [FrontpageconfigDeleteDialogComponent]
})
export class SpingularFrontpageconfigModule {}
