import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IFrontpageconfig, Frontpageconfig } from 'app/shared/model/frontpageconfig.model';
import { FrontpageconfigService } from './frontpageconfig.service';

@Component({
  selector: 'jhi-frontpageconfig-update',
  templateUrl: './frontpageconfig-update.component.html'
})
export class FrontpageconfigUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    topNews1: [],
    topNews2: [],
    topNews3: [],
    topNews4: [],
    topNews5: [],
    latestNews1: [],
    latestNews2: [],
    latestNews3: [],
    latestNews4: [],
    latestNews5: [],
    breakingNews1: [],
    recentPosts1: [],
    recentPosts2: [],
    recentPosts3: [],
    recentPosts4: [],
    featuredArticles1: [],
    featuredArticles2: [],
    featuredArticles3: [],
    featuredArticles4: [],
    featuredArticles5: [],
    featuredArticles6: [],
    featuredArticles7: [],
    featuredArticles8: [],
    featuredArticles9: [],
    featuredArticles10: [],
    popularNews1: [],
    popularNews2: [],
    popularNews3: [],
    popularNews4: [],
    popularNews5: [],
    popularNews6: [],
    popularNews7: [],
    popularNews8: [],
    weeklyNews1: [],
    weeklyNews2: [],
    weeklyNews3: [],
    weeklyNews4: [],
    newsFeeds1: [],
    newsFeeds2: [],
    newsFeeds3: [],
    newsFeeds4: [],
    newsFeeds5: [],
    newsFeeds6: [],
    usefulLinks1: [],
    usefulLinks2: [],
    usefulLinks3: [],
    usefulLinks4: [],
    usefulLinks5: [],
    usefulLinks6: [],
    recentVideos1: [],
    recentVideos2: [],
    recentVideos3: [],
    recentVideos4: [],
    recentVideos5: [],
    recentVideos6: []
  });

  constructor(
    protected frontpageconfigService: FrontpageconfigService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ frontpageconfig }) => {
      this.updateForm(frontpageconfig);
    });
  }

  updateForm(frontpageconfig: IFrontpageconfig) {
    this.editForm.patchValue({
      id: frontpageconfig.id,
      creationDate: frontpageconfig.creationDate != null ? frontpageconfig.creationDate.format(DATE_TIME_FORMAT) : null,
      topNews1: frontpageconfig.topNews1,
      topNews2: frontpageconfig.topNews2,
      topNews3: frontpageconfig.topNews3,
      topNews4: frontpageconfig.topNews4,
      topNews5: frontpageconfig.topNews5,
      latestNews1: frontpageconfig.latestNews1,
      latestNews2: frontpageconfig.latestNews2,
      latestNews3: frontpageconfig.latestNews3,
      latestNews4: frontpageconfig.latestNews4,
      latestNews5: frontpageconfig.latestNews5,
      breakingNews1: frontpageconfig.breakingNews1,
      recentPosts1: frontpageconfig.recentPosts1,
      recentPosts2: frontpageconfig.recentPosts2,
      recentPosts3: frontpageconfig.recentPosts3,
      recentPosts4: frontpageconfig.recentPosts4,
      featuredArticles1: frontpageconfig.featuredArticles1,
      featuredArticles2: frontpageconfig.featuredArticles2,
      featuredArticles3: frontpageconfig.featuredArticles3,
      featuredArticles4: frontpageconfig.featuredArticles4,
      featuredArticles5: frontpageconfig.featuredArticles5,
      featuredArticles6: frontpageconfig.featuredArticles6,
      featuredArticles7: frontpageconfig.featuredArticles7,
      featuredArticles8: frontpageconfig.featuredArticles8,
      featuredArticles9: frontpageconfig.featuredArticles9,
      featuredArticles10: frontpageconfig.featuredArticles10,
      popularNews1: frontpageconfig.popularNews1,
      popularNews2: frontpageconfig.popularNews2,
      popularNews3: frontpageconfig.popularNews3,
      popularNews4: frontpageconfig.popularNews4,
      popularNews5: frontpageconfig.popularNews5,
      popularNews6: frontpageconfig.popularNews6,
      popularNews7: frontpageconfig.popularNews7,
      popularNews8: frontpageconfig.popularNews8,
      weeklyNews1: frontpageconfig.weeklyNews1,
      weeklyNews2: frontpageconfig.weeklyNews2,
      weeklyNews3: frontpageconfig.weeklyNews3,
      weeklyNews4: frontpageconfig.weeklyNews4,
      newsFeeds1: frontpageconfig.newsFeeds1,
      newsFeeds2: frontpageconfig.newsFeeds2,
      newsFeeds3: frontpageconfig.newsFeeds3,
      newsFeeds4: frontpageconfig.newsFeeds4,
      newsFeeds5: frontpageconfig.newsFeeds5,
      newsFeeds6: frontpageconfig.newsFeeds6,
      usefulLinks1: frontpageconfig.usefulLinks1,
      usefulLinks2: frontpageconfig.usefulLinks2,
      usefulLinks3: frontpageconfig.usefulLinks3,
      usefulLinks4: frontpageconfig.usefulLinks4,
      usefulLinks5: frontpageconfig.usefulLinks5,
      usefulLinks6: frontpageconfig.usefulLinks6,
      recentVideos1: frontpageconfig.recentVideos1,
      recentVideos2: frontpageconfig.recentVideos2,
      recentVideos3: frontpageconfig.recentVideos3,
      recentVideos4: frontpageconfig.recentVideos4,
      recentVideos5: frontpageconfig.recentVideos5,
      recentVideos6: frontpageconfig.recentVideos6
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const frontpageconfig = this.createFromForm();
    if (frontpageconfig.id !== undefined) {
      this.subscribeToSaveResponse(this.frontpageconfigService.update(frontpageconfig));
    } else {
      this.subscribeToSaveResponse(this.frontpageconfigService.create(frontpageconfig));
    }
  }

  private createFromForm(): IFrontpageconfig {
    return {
      ...new Frontpageconfig(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      topNews1: this.editForm.get(['topNews1']).value,
      topNews2: this.editForm.get(['topNews2']).value,
      topNews3: this.editForm.get(['topNews3']).value,
      topNews4: this.editForm.get(['topNews4']).value,
      topNews5: this.editForm.get(['topNews5']).value,
      latestNews1: this.editForm.get(['latestNews1']).value,
      latestNews2: this.editForm.get(['latestNews2']).value,
      latestNews3: this.editForm.get(['latestNews3']).value,
      latestNews4: this.editForm.get(['latestNews4']).value,
      latestNews5: this.editForm.get(['latestNews5']).value,
      breakingNews1: this.editForm.get(['breakingNews1']).value,
      recentPosts1: this.editForm.get(['recentPosts1']).value,
      recentPosts2: this.editForm.get(['recentPosts2']).value,
      recentPosts3: this.editForm.get(['recentPosts3']).value,
      recentPosts4: this.editForm.get(['recentPosts4']).value,
      featuredArticles1: this.editForm.get(['featuredArticles1']).value,
      featuredArticles2: this.editForm.get(['featuredArticles2']).value,
      featuredArticles3: this.editForm.get(['featuredArticles3']).value,
      featuredArticles4: this.editForm.get(['featuredArticles4']).value,
      featuredArticles5: this.editForm.get(['featuredArticles5']).value,
      featuredArticles6: this.editForm.get(['featuredArticles6']).value,
      featuredArticles7: this.editForm.get(['featuredArticles7']).value,
      featuredArticles8: this.editForm.get(['featuredArticles8']).value,
      featuredArticles9: this.editForm.get(['featuredArticles9']).value,
      featuredArticles10: this.editForm.get(['featuredArticles10']).value,
      popularNews1: this.editForm.get(['popularNews1']).value,
      popularNews2: this.editForm.get(['popularNews2']).value,
      popularNews3: this.editForm.get(['popularNews3']).value,
      popularNews4: this.editForm.get(['popularNews4']).value,
      popularNews5: this.editForm.get(['popularNews5']).value,
      popularNews6: this.editForm.get(['popularNews6']).value,
      popularNews7: this.editForm.get(['popularNews7']).value,
      popularNews8: this.editForm.get(['popularNews8']).value,
      weeklyNews1: this.editForm.get(['weeklyNews1']).value,
      weeklyNews2: this.editForm.get(['weeklyNews2']).value,
      weeklyNews3: this.editForm.get(['weeklyNews3']).value,
      weeklyNews4: this.editForm.get(['weeklyNews4']).value,
      newsFeeds1: this.editForm.get(['newsFeeds1']).value,
      newsFeeds2: this.editForm.get(['newsFeeds2']).value,
      newsFeeds3: this.editForm.get(['newsFeeds3']).value,
      newsFeeds4: this.editForm.get(['newsFeeds4']).value,
      newsFeeds5: this.editForm.get(['newsFeeds5']).value,
      newsFeeds6: this.editForm.get(['newsFeeds6']).value,
      usefulLinks1: this.editForm.get(['usefulLinks1']).value,
      usefulLinks2: this.editForm.get(['usefulLinks2']).value,
      usefulLinks3: this.editForm.get(['usefulLinks3']).value,
      usefulLinks4: this.editForm.get(['usefulLinks4']).value,
      usefulLinks5: this.editForm.get(['usefulLinks5']).value,
      usefulLinks6: this.editForm.get(['usefulLinks6']).value,
      recentVideos1: this.editForm.get(['recentVideos1']).value,
      recentVideos2: this.editForm.get(['recentVideos2']).value,
      recentVideos3: this.editForm.get(['recentVideos3']).value,
      recentVideos4: this.editForm.get(['recentVideos4']).value,
      recentVideos5: this.editForm.get(['recentVideos5']).value,
      recentVideos6: this.editForm.get(['recentVideos6']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFrontpageconfig>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
