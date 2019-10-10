import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpingularSharedModule } from 'app/shared/shared.module';
import { PostComponent } from './post.component';
import { PostDetailComponent } from './post-detail.component';
import { PostUpdateComponent } from './post-update.component';
import { PostDeletePopupComponent, PostDeleteDialogComponent } from './post-delete-dialog.component';
import { postRoute, postPopupRoute } from './post.route';

const ENTITY_STATES = [...postRoute, ...postPopupRoute];

@NgModule({
  imports: [SpingularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PostComponent, PostDetailComponent, PostUpdateComponent, PostDeleteDialogComponent, PostDeletePopupComponent],
  entryComponents: [PostDeleteDialogComponent]
})
export class SpingularPostModule {}
