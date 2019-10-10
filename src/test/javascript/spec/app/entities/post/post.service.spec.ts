import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PostService } from 'app/entities/post/post.service';
import { IPost, Post } from 'app/shared/model/post.model';

describe('Service Tests', () => {
  describe('Post Service', () => {
    let injector: TestBed;
    let service: PostService;
    let httpMock: HttpTestingController;
    let elemDefault: IPost;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PostService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Post(
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            publicationDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Post', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            publicationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            publicationDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Post(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Post', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            publicationDate: currentDate.format(DATE_TIME_FORMAT),
            headline: 'BBBBBB',
            leadtext: 'BBBBBB',
            bodytext: 'BBBBBB',
            quote: 'BBBBBB',
            conclusion: 'BBBBBB',
            linkText: 'BBBBBB',
            linkURL: 'BBBBBB',
            image: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            publicationDate: currentDate
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

      it('should return a list of Post', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            publicationDate: currentDate.format(DATE_TIME_FORMAT),
            headline: 'BBBBBB',
            leadtext: 'BBBBBB',
            bodytext: 'BBBBBB',
            quote: 'BBBBBB',
            conclusion: 'BBBBBB',
            linkText: 'BBBBBB',
            linkURL: 'BBBBBB',
            image: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            publicationDate: currentDate
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

      it('should delete a Post', () => {
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
