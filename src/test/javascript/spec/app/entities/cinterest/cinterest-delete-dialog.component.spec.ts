import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SpingularTestModule } from '../../../test.module';
import { CinterestDeleteDialogComponent } from 'app/entities/cinterest/cinterest-delete-dialog.component';
import { CinterestService } from 'app/entities/cinterest/cinterest.service';

describe('Component Tests', () => {
  describe('Cinterest Management Delete Component', () => {
    let comp: CinterestDeleteDialogComponent;
    let fixture: ComponentFixture<CinterestDeleteDialogComponent>;
    let service: CinterestService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [CinterestDeleteDialogComponent]
      })
        .overrideTemplate(CinterestDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CinterestDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CinterestService);
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
