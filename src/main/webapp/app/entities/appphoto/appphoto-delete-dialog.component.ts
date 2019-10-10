import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppphoto } from 'app/shared/model/appphoto.model';
import { AppphotoService } from './appphoto.service';

@Component({
  selector: 'jhi-appphoto-delete-dialog',
  templateUrl: './appphoto-delete-dialog.component.html'
})
export class AppphotoDeleteDialogComponent {
  appphoto: IAppphoto;

  constructor(protected appphotoService: AppphotoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.appphotoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'appphotoListModification',
        content: 'Deleted an appphoto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-appphoto-delete-popup',
  template: ''
})
export class AppphotoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appphoto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AppphotoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.appphoto = appphoto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/appphoto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/appphoto', { outlets: { popup: null } }]);
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
