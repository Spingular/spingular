import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SpingularTestModule } from '../../../test.module';
import { ProposalDeleteDialogComponent } from 'app/entities/proposal/proposal-delete-dialog.component';
import { ProposalService } from 'app/entities/proposal/proposal.service';

describe('Component Tests', () => {
  describe('Proposal Management Delete Component', () => {
    let comp: ProposalDeleteDialogComponent;
    let fixture: ComponentFixture<ProposalDeleteDialogComponent>;
    let service: ProposalService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [ProposalDeleteDialogComponent]
      })
        .overrideTemplate(ProposalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProposalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProposalService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
