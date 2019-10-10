import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { VquestionComponent } from './vquestion.component';
import { VquestionDetailComponent } from './vquestion-detail.component';
import { VquestionUpdateComponent } from './vquestion-update.component';
import { VquestionDeletePopupComponent, VquestionDeleteDialogComponent } from './vquestion-delete-dialog.component';
import { vquestionRoute, vquestionPopupRoute } from './vquestion.route';

const ENTITY_STATES = [...vquestionRoute, ...vquestionPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VquestionComponent,
    VquestionDetailComponent,
    VquestionUpdateComponent,
    VquestionDeleteDialogComponent,
    VquestionDeletePopupComponent
  ],
  entryComponents: [VquestionDeleteDialogComponent]
})
export class SpingularVquestionModule {}
