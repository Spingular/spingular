import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SpingularTestModule } from '../../../test.module';
import { AppprofileDeleteDialogComponent } from 'app/entities/appprofile/appprofile-delete-dialog.component';
import { AppprofileService } from 'app/entities/appprofile/appprofile.service';

describe('Component Tests', () => {
  describe('Appprofile Management Delete Component', () => {
    let comp: AppprofileDeleteDialogComponent;
    let fixture: ComponentFixture<AppprofileDeleteDialogComponent>;
    let service: AppprofileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [AppprofileDeleteDialogComponent]
      })
        .overrideTemplate(AppprofileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppprofileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppprofileService);
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
