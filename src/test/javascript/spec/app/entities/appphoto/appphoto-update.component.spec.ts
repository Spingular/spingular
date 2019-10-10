import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { AppphotoUpdateComponent } from 'app/entities/appphoto/appphoto-update.component';
import { AppphotoService } from 'app/entities/appphoto/appphoto.service';
import { Appphoto } from 'app/shared/model/appphoto.model';

describe('Component Tests', () => {
  describe('Appphoto Management Update Component', () => {
    let comp: AppphotoUpdateComponent;
    let fixture: ComponentFixture<AppphotoUpdateComponent>;
    let service: AppphotoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [AppphotoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AppphotoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppphotoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppphotoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Appphoto(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Appphoto();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
