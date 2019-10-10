import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITopic, Topic } from 'app/shared/model/topic.model';
import { TopicService } from './topic.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';

@Component({
  selector: 'jhi-topic-update',
  templateUrl: './topic-update.component.html'
})
export class TopicUpdateComponent implements OnInit {
  isSaving: boolean;

  posts: IPost[];

  editForm = this.fb.group({
    id: [],
    topicName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    posts: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected topicService: TopicService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ topic }) => {
      this.updateForm(topic);
    });
    this.postService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPost[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPost[]>) => response.body)
      )
      .subscribe((res: IPost[]) => (this.posts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(topic: ITopic) {
    this.editForm.patchValue({
      id: topic.id,
      topicName: topic.topicName,
      posts: topic.posts
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const topic = this.createFromForm();
    if (topic.id !== undefined) {
      this.subscribeToSaveResponse(this.topicService.update(topic));
    } else {
      this.subscribeToSaveResponse(this.topicService.create(topic));
    }
  }

  private createFromForm(): ITopic {
    return {
      ...new Topic(),
      id: this.editForm.get(['id']).value,
      topicName: this.editForm.get(['topicName']).value,
      posts: this.editForm.get(['posts']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopic>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPostById(index: number, item: IPost) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
