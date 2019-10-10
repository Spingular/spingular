import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { CommentComponent } from './comment.component';
import { CommentDetailComponent } from './comment-detail.component';
import { CommentUpdateComponent } from './comment-update.component';
import { CommentDeletePopupComponent, CommentDeleteDialogComponent } from './comment-delete-dialog.component';
import { commentRoute, commentPopupRoute } from './comment.route';

const ENTITY_STATES = [...commentRoute, ...commentPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CommentComponent,
    CommentDetailComponent,
    CommentUpdateComponent,
    CommentDeleteDialogComponent,
    CommentDeletePopupComponent
  ],
  entryComponents: [CommentDeleteDialogComponent]
})
export class SpingularCommentModule {}
