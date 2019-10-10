import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from './appuser.service';

@Component({
  selector: 'jhi-appuser-delete-dialog',
  templateUrl: './appuser-delete-dialog.component.html'
})
export class AppuserDeleteDialogComponent {
  appuser: IAppuser;

  constructor(protected appuserService: AppuserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.appuserService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'appuserListModification',
        content: 'Deleted an appuser'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-appuser-delete-popup',
  template: ''
})
export class AppuserDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appuser }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AppuserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.appuser = appuser;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/appuser', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/appuser', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
