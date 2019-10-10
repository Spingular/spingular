import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SpingularTestModule } from '../../../test.module';
import { BlockuserDeleteDialogComponent } from 'app/entities/blockuser/blockuser-delete-dialog.component';
import { BlockuserService } from 'app/entities/blockuser/blockuser.service';

describe('Component Tests', () => {
  describe('Blockuser Management Delete Component', () => {
    let comp: BlockuserDeleteDialogComponent;
    let fixture: ComponentFixture<BlockuserDeleteDialogComponent>;
    let service: BlockuserService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [BlockuserDeleteDialogComponent]
      })
        .overrideTemplate(BlockuserDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BlockuserDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlockuserService);
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
