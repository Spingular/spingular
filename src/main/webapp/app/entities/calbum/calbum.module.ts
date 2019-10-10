import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { CalbumComponent } from './calbum.component';
import { CalbumDetailComponent } from './calbum-detail.component';
import { CalbumUpdateComponent } from './calbum-update.component';
import { CalbumDeletePopupComponent, CalbumDeleteDialogComponent } from './calbum-delete-dialog.component';
import { calbumRoute, calbumPopupRoute } from './calbum.route';

const ENTITY_STATES = [...calbumRoute, ...calbumPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CalbumComponent, CalbumDetailComponent, CalbumUpdateComponent, CalbumDeleteDialogComponent, CalbumDeletePopupComponent],
  entryComponents: [CalbumDeleteDialogComponent]
})
export class SpingularCalbumModule {}
