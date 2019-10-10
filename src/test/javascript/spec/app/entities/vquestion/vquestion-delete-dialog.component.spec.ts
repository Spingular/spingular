import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SpingularTestModule } from '../../../test.module';
import { VquestionDeleteDialogComponent } from 'app/entities/vquestion/vquestion-delete-dialog.component';
import { VquestionService } from 'app/entities/vquestion/vquestion.service';

describe('Component Tests', () => {
  describe('Vquestion Management Delete Component', () => {
    let comp: VquestionDeleteDialogComponent;
    let fixture: ComponentFixture<VquestionDeleteDialogComponent>;
    let service: VquestionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [VquestionDeleteDialogComponent]
      })
        .overrideTemplate(VquestionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VquestionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VquestionService);
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
