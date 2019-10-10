import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpingularTestModule } from '../../../test.module';
import { AppphotoDetailComponent } from 'app/entities/appphoto/appphoto-detail.component';
import { Appphoto } from 'app/shared/model/appphoto.model';

describe('Component Tests', () => {
  describe('Appphoto Management Detail Component', () => {
    let comp: AppphotoDetailComponent;
    let fixture: ComponentFixture<AppphotoDetailComponent>;
    const route = ({ data: of({ appphoto: new Appphoto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpingularTestModule],
        declarations: [AppphotoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AppphotoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppphotoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appphoto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
