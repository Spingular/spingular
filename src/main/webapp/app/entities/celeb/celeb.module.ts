import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { CelebComponent } from './celeb.component';
import { CelebDetailComponent } from './celeb-detail.component';
import { CelebUpdateComponent } from './celeb-update.component';
import { CelebDeletePopupComponent, CelebDeleteDialogComponent } from './celeb-delete-dialog.component';
import { celebRoute, celebPopupRoute } from './celeb.route';

const ENTITY_STATES = [...celebRoute, ...celebPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CelebComponent, CelebDetailComponent, CelebUpdateComponent, CelebDeleteDialogComponent, CelebDeletePopupComponent],
  entryComponents: [CelebDeleteDialogComponent]
})
export class SpingularCelebModule {}
