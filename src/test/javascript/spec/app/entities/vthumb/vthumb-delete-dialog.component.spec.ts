import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SpingularTestModule } from '../../../test.module';
import { VthumbDeleteDialogComponent } from 'app/entities/vthumb/vthumb-delete-dialog.component';
import { VthumbService } from 'app/entities/vthumb/vthumb.service';

describe('Component Tests', () => {
  describe('Vthumb Management Delete Component', () => {
    let comp: VthumbDeleteDialogComponent;
    let fixture: ComponentFixture<VthumbDeleteDialogComponent>;
    let service: VthumbService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [VthumbDeleteDialogComponent]
      })
        .overrideTemplate(VthumbDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VthumbDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VthumbService);
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
