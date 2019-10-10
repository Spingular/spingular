import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from './message.service';

@Component({
  selector: 'jhi-message-delete-dialog',
  templateUrl: './message-delete-dialog.component.html'
})
export class MessageDeleteDialogComponent {
  message: IMessage;

  constructor(protected messageService: MessageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.messageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'messageListModification',
        content: 'Deleted an message'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-message-delete-popup',
  template: ''
})
export class MessageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ message }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MessageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.message = message;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/message', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/message', { outlets: { popup: null } }]);
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
