import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AppprofileService } from 'app/entities/appprofile/appprofile.service';
import { IAppprofile, Appprofile } from 'app/shared/model/appprofile.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { CivilStatus } from 'app/shared/model/enumerations/civil-status.model';
import { Purpose } from 'app/shared/model/enumerations/purpose.model';
import { Physical } from 'app/shared/model/enumerations/physical.model';
import { Religion } from 'app/shared/model/enumerations/religion.model';
import { EthnicGroup } from 'app/shared/model/enumerations/ethnic-group.model';
import { Studies } from 'app/shared/model/enumerations/studies.model';
import { Eyes } from 'app/shared/model/enumerations/eyes.model';
import { Smoker } from 'app/shared/model/enumerations/smoker.model';
import { Children } from 'app/shared/model/enumerations/children.model';
import { FutureChildren } from 'app/shared/model/enumerations/future-children.model';

describe('Service Tests', () => {
  describe('Appprofile Service', () => {
    let injector: TestBed;
    let service: AppprofileService;
    let httpMock: HttpTestingController;
    let elemDefault: IAppprofile;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AppprofileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Appprofile(
        0,
        currentDate,
        Gender.MALE,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        CivilStatus.NA,
        Gender.MALE,
        Purpose.NOT_INTERESTED,
        Physical.NA,
        Religion.NA,
        EthnicGroup.NA,
        Studies.NA,
        0,
        Eyes.NA,
        Smoker.NA,
        Children.NA,
        FutureChildren.NA,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            birthdate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Appprofile', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            birthdate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            birthdate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Appprofile(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Appprofile', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            gender: 'BBBBBB',
            phone: 'BBBBBB',
            bio: 'BBBBBB',
            facebook: 'BBBBBB',
            twitter: 'BBBBBB',
            linkedin: 'BBBBBB',
            instagram: 'BBBBBB',
            googlePlus: 'BBBBBB',
            birthdate: currentDate.format(DATE_TIME_FORMAT),
            civilStatus: 'BBBBBB',
            lookingFor: 'BBBBBB',
            purpose: 'BBBBBB',
            physical: 'BBBBBB',
            religion: 'BBBBBB',
            ethnicGroup: 'BBBBBB',
            studies: 'BBBBBB',
            sibblings: 1,
            eyes: 'BBBBBB',
            smoker: 'BBBBBB',
            children: 'BBBBBB',
            futureChildren: 'BBBBBB',
            pet: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            birthdate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Appprofile', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            gender: 'BBBBBB',
            phone: 'BBBBBB',
            bio: 'BBBBBB',
            facebook: 'BBBBBB',
            twitter: 'BBBBBB',
            linkedin: 'BBBBBB',
            instagram: 'BBBBBB',
            googlePlus: 'BBBBBB',
            birthdate: currentDate.format(DATE_TIME_FORMAT),
            civilStatus: 'BBBBBB',
            lookingFor: 'BBBBBB',
            purpose: 'BBBBBB',
            physical: 'BBBBBB',
            religion: 'BBBBBB',
            ethnicGroup: 'BBBBBB',
            studies: 'BBBBBB',
            sibblings: 1,
            eyes: 'BBBBBB',
            smoker: 'BBBBBB',
            children: 'BBBBBB',
            futureChildren: 'BBBBBB',
            pet: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            birthdate: currentDate
          },
          returnedFromService
        );
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

      it('should delete a Appprofile', () => {
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
