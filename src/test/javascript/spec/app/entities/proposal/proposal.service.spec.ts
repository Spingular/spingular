import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProposalService } from 'app/entities/proposal/proposal.service';
import { IProposal, Proposal } from 'app/shared/model/proposal.model';
import { ProposalType } from 'app/shared/model/enumerations/proposal-type.model';
import { ProposalRole } from 'app/shared/model/enumerations/proposal-role.model';

describe('Service Tests', () => {
  describe('Proposal Service', () => {
    let injector: TestBed;
    let service: ProposalService;
    let httpMock: HttpTestingController;
    let elemDefault: IProposal;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProposalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Proposal(0, currentDate, 'AAAAAAA', ProposalType.STUDY, ProposalRole.USER, currentDate, false, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            releaseDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Proposal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            releaseDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            releaseDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Proposal(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Proposal', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            proposalName: 'BBBBBB',
            proposalType: 'BBBBBB',
            proposalRole: 'BBBBBB',
            releaseDate: currentDate.format(DATE_TIME_FORMAT),
            isOpen: true,
            isAccepted: true,
            isPaid: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            releaseDate: currentDate
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

      it('should return a list of Proposal', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            proposalName: 'BBBBBB',
            proposalType: 'BBBBBB',
            proposalRole: 'BBBBBB',
            releaseDate: currentDate.format(DATE_TIME_FORMAT),
            isOpen: true,
            isAccepted: true,
            isPaid: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            releaseDate: currentDate
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

      it('should delete a Proposal', () => {
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
