import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'blog',
        loadChildren: () => import('./blog/blog.module').then(m => m.SpingularBlogModule)
      },
      {
        path: 'post',
        loadChildren: () => import('./post/post.module').then(m => m.SpingularPostModule)
      },
      {
        path: 'topic',
        loadChildren: () => import('./topic/topic.module').then(m => m.SpingularTopicModule)
      },
      {
        path: 'tag',
        loadChildren: () => import('./tag/tag.module').then(m => m.SpingularTagModule)
      },
      {
        path: 'comment',
        loadChildren: () => import('./comment/comment.module').then(m => m.SpingularCommentModule)
      },
      {
        path: 'cmessage',
        loadChildren: () => import('./cmessage/cmessage.module').then(m => m.SpingularCmessageModule)
      },
      {
        path: 'message',
        loadChildren: () => import('./message/message.module').then(m => m.SpingularMessageModule)
      },
      {
        path: 'notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.SpingularNotificationModule)
      },
      {
        path: 'appphoto',
        loadChildren: () => import('./appphoto/appphoto.module').then(m => m.SpingularAppphotoModule)
      },
      {
        path: 'appprofile',
        loadChildren: () => import('./appprofile/appprofile.module').then(m => m.SpingularAppprofileModule)
      },
      {
        path: 'community',
        loadChildren: () => import('./community/community.module').then(m => m.SpingularCommunityModule)
      },
      {
        path: 'follow',
        loadChildren: () => import('./follow/follow.module').then(m => m.SpingularFollowModule)
      },
      {
        path: 'blockuser',
        loadChildren: () => import('./blockuser/blockuser.module').then(m => m.SpingularBlockuserModule)
      },
      {
        path: 'album',
        loadChildren: () => import('./album/album.module').then(m => m.SpingularAlbumModule)
      },
      {
        path: 'calbum',
        loadChildren: () => import('./calbum/calbum.module').then(m => m.SpingularCalbumModule)
      },
      {
        path: 'photo',
        loadChildren: () => import('./photo/photo.module').then(m => m.SpingularPhotoModule)
      },
      {
        path: 'interest',
        loadChildren: () => import('./interest/interest.module').then(m => m.SpingularInterestModule)
      },
      {
        path: 'activity',
        loadChildren: () => import('./activity/activity.module').then(m => m.SpingularActivityModule)
      },
      {
        path: 'celeb',
        loadChildren: () => import('./celeb/celeb.module').then(m => m.SpingularCelebModule)
      },
      {
        path: 'cinterest',
        loadChildren: () => import('./cinterest/cinterest.module').then(m => m.SpingularCinterestModule)
      },
      {
        path: 'cactivity',
        loadChildren: () => import('./cactivity/cactivity.module').then(m => m.SpingularCactivityModule)
      },
      {
        path: 'cceleb',
        loadChildren: () => import('./cceleb/cceleb.module').then(m => m.SpingularCcelebModule)
      },
      {
        path: 'urllink',
        loadChildren: () => import('./urllink/urllink.module').then(m => m.SpingularUrllinkModule)
      },
      {
        path: 'frontpageconfig',
        loadChildren: () => import('./frontpageconfig/frontpageconfig.module').then(m => m.SpingularFrontpageconfigModule)
      },
      {
        path: 'vtopic',
        loadChildren: () => import('./vtopic/vtopic.module').then(m => m.SpingularVtopicModule)
      },
      {
        path: 'vquestion',
        loadChildren: () => import('./vquestion/vquestion.module').then(m => m.SpingularVquestionModule)
      },
      {
        path: 'vanswer',
        loadChildren: () => import('./vanswer/vanswer.module').then(m => m.SpingularVanswerModule)
      },
      {
        path: 'vthumb',
        loadChildren: () => import('./vthumb/vthumb.module').then(m => m.SpingularVthumbModule)
      },
      {
        path: 'newsletter',
        loadChildren: () => import('./newsletter/newsletter.module').then(m => m.SpingularNewsletterModule)
      },
      {
        path: 'feedback',
        loadChildren: () => import('./feedback/feedback.module').then(m => m.SpingularFeedbackModule)
      },
      {
        path: 'config-variables',
        loadChildren: () => import('./config-variables/config-variables.module').then(m => m.SpingularConfigVariablesModule)
      },
      {
        path: 'proposal',
        loadChildren: () => import('./proposal/proposal.module').then(m => m.SpingularProposalModule)
      },
      {
        path: 'proposal-vote',
        loadChildren: () => import('./proposal-vote/proposal-vote.module').then(m => m.SpingularProposalVoteModule)
      },
      {
        path: 'appuser',
        loadChildren: () => import('./appuser/appuser.module').then(m => m.SpingularAppuserModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SpingularEntityModule {}
