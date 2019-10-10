import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { ConfigVariablesService } from 'app/entities/config-variables/config-variables.service';
import { IConfigVariables, ConfigVariables } from 'app/shared/model/config-variables.model';

describe('Service Tests', () => {
  describe('ConfigVariables Service', () => {
    let injector: TestBed;
    let service: ConfigVariablesService;
    let httpMock: HttpTestingController;
    let elemDefault: IConfigVariables;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ConfigVariablesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ConfigVariables(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ConfigVariables', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new ConfigVariables(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ConfigVariables', () => {
        const returnedFromService = Object.assign(
          {
            configVarLong1: 1,
            configVarLong2: 1,
            configVarLong3: 1,
            configVarLong4: 1,
            configVarLong5: 1,
            configVarLong6: 1,
            configVarLong7: 1,
            configVarLong8: 1,
            configVarLong9: 1,
            configVarLong10: 1,
            configVarLong11: 1,
            configVarLong12: 1,
            configVarLong13: 1,
            configVarLong14: 1,
            configVarLong15: 1,
            configVarBoolean16: true,
            configVarBoolean17: true,
            configVarBoolean18: true,
            configVarString19: 'BBBBBB',
            configVarString20: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ConfigVariables', () => {
        const returnedFromService = Object.assign(
          {
            configVarLong1: 1,
            configVarLong2: 1,
            configVarLong3: 1,
            configVarLong4: 1,
            configVarLong5: 1,
            configVarLong6: 1,
            configVarLong7: 1,
            configVarLong8: 1,
            configVarLong9: 1,
            configVarLong10: 1,
            configVarLong11: 1,
            configVarLong12: 1,
            configVarLong13: 1,
            configVarLong14: 1,
            configVarLong15: 1,
            configVarBoolean16: true,
            configVarBoolean17: true,
            configVarBoolean18: true,
            configVarString19: 'BBBBBB',
            configVarString20: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ConfigVariables', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
