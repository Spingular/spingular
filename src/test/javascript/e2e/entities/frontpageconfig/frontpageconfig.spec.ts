// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FrontpageconfigComponentsPage, FrontpageconfigDeleteDialog, FrontpageconfigUpdatePage } from './frontpageconfig.page-object';

const expect = chai.expect;

describe('Frontpageconfig e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let frontpageconfigComponentsPage: FrontpageconfigComponentsPage;
  let frontpageconfigUpdatePage: FrontpageconfigUpdatePage;
  let frontpageconfigDeleteDialog: FrontpageconfigDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Frontpageconfigs', async () => {
    await navBarPage.goToEntity('frontpageconfig');
    frontpageconfigComponentsPage = new FrontpageconfigComponentsPage();
    await browser.wait(ec.visibilityOf(frontpageconfigComponentsPage.title), 5000);
    expect(await frontpageconfigComponentsPage.getTitle()).to.eq('spingularApp.frontpageconfig.home.title');
  });

  it('should load create Frontpageconfig page', async () => {
    await frontpageconfigComponentsPage.clickOnCreateButton();
    frontpageconfigUpdatePage = new FrontpageconfigUpdatePage();
    expect(await frontpageconfigUpdatePage.getPageTitle()).to.eq('spingularApp.frontpageconfig.home.createOrEditLabel');
    await frontpageconfigUpdatePage.cancel();
  });

  it('should create and save Frontpageconfigs', async () => {
    const nbButtonsBeforeCreate = await frontpageconfigComponentsPage.countDeleteButtons();

    await frontpageconfigComponentsPage.clickOnCreateButton();
    await promise.all([
      frontpageconfigUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      frontpageconfigUpdatePage.setTopNews1Input('5'),
      frontpageconfigUpdatePage.setTopNews2Input('5'),
      frontpageconfigUpdatePage.setTopNews3Input('5'),
      frontpageconfigUpdatePage.setTopNews4Input('5'),
      frontpageconfigUpdatePage.setTopNews5Input('5'),
      frontpageconfigUpdatePage.setLatestNews1Input('5'),
      frontpageconfigUpdatePage.setLatestNews2Input('5'),
      frontpageconfigUpdatePage.setLatestNews3Input('5'),
      frontpageconfigUpdatePage.setLatestNews4Input('5'),
      frontpageconfigUpdatePage.setLatestNews5Input('5'),
      frontpageconfigUpdatePage.setBreakingNews1Input('5'),
      frontpageconfigUpdatePage.setRecentPosts1Input('5'),
      frontpageconfigUpdatePage.setRecentPosts2Input('5'),
      frontpageconfigUpdatePage.setRecentPosts3Input('5'),
      frontpageconfigUpdatePage.setRecentPosts4Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles1Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles2Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles3Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles4Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles5Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles6Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles7Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles8Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles9Input('5'),
      frontpageconfigUpdatePage.setFeaturedArticles10Input('5'),
      frontpageconfigUpdatePage.setPopularNews1Input('5'),
      frontpageconfigUpdatePage.setPopularNews2Input('5'),
      frontpageconfigUpdatePage.setPopularNews3Input('5'),
      frontpageconfigUpdatePage.setPopularNews4Input('5'),
      frontpageconfigUpdatePage.setPopularNews5Input('5'),
      frontpageconfigUpdatePage.setPopularNews6Input('5'),
      frontpageconfigUpdatePage.setPopularNews7Input('5'),
      frontpageconfigUpdatePage.setPopularNews8Input('5'),
      frontpageconfigUpdatePage.setWeeklyNews1Input('5'),
      frontpageconfigUpdatePage.setWeeklyNews2Input('5'),
      frontpageconfigUpdatePage.setWeeklyNews3Input('5'),
      frontpageconfigUpdatePage.setWeeklyNews4Input('5'),
      frontpageconfigUpdatePage.setNewsFeeds1Input('5'),
      frontpageconfigUpdatePage.setNewsFeeds2Input('5'),
      frontpageconfigUpdatePage.setNewsFeeds3Input('5'),
      frontpageconfigUpdatePage.setNewsFeeds4Input('5'),
      frontpageconfigUpdatePage.setNewsFeeds5Input('5'),
      frontpageconfigUpdatePage.setNewsFeeds6Input('5'),
      frontpageconfigUpdatePage.setUsefulLinks1Input('5'),
      frontpageconfigUpdatePage.setUsefulLinks2Input('5'),
      frontpageconfigUpdatePage.setUsefulLinks3Input('5'),
      frontpageconfigUpdatePage.setUsefulLinks4Input('5'),
      frontpageconfigUpdatePage.setUsefulLinks5Input('5'),
      frontpageconfigUpdatePage.setUsefulLinks6Input('5'),
      frontpageconfigUpdatePage.setRecentVideos1Input('5'),
      frontpageconfigUpdatePage.setRecentVideos2Input('5'),
      frontpageconfigUpdatePage.setRecentVideos3Input('5'),
      frontpageconfigUpdatePage.setRecentVideos4Input('5'),
      frontpageconfigUpdatePage.setRecentVideos5Input('5'),
      frontpageconfigUpdatePage.setRecentVideos6Input('5')
    ]);
    expect(await frontpageconfigUpdatePage.getCreationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationDate value to be equals to 2000-12-31'
    );
    expect(await frontpageconfigUpdatePage.getTopNews1Input()).to.eq('5', 'Expected topNews1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getTopNews2Input()).to.eq('5', 'Expected topNews2 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getTopNews3Input()).to.eq('5', 'Expected topNews3 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getTopNews4Input()).to.eq('5', 'Expected topNews4 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getTopNews5Input()).to.eq('5', 'Expected topNews5 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getLatestNews1Input()).to.eq('5', 'Expected latestNews1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getLatestNews2Input()).to.eq('5', 'Expected latestNews2 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getLatestNews3Input()).to.eq('5', 'Expected latestNews3 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getLatestNews4Input()).to.eq('5', 'Expected latestNews4 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getLatestNews5Input()).to.eq('5', 'Expected latestNews5 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getBreakingNews1Input()).to.eq('5', 'Expected breakingNews1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentPosts1Input()).to.eq('5', 'Expected recentPosts1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentPosts2Input()).to.eq('5', 'Expected recentPosts2 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentPosts3Input()).to.eq('5', 'Expected recentPosts3 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentPosts4Input()).to.eq('5', 'Expected recentPosts4 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles1Input()).to.eq('5', 'Expected featuredArticles1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles2Input()).to.eq('5', 'Expected featuredArticles2 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles3Input()).to.eq('5', 'Expected featuredArticles3 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles4Input()).to.eq('5', 'Expected featuredArticles4 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles5Input()).to.eq('5', 'Expected featuredArticles5 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles6Input()).to.eq('5', 'Expected featuredArticles6 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles7Input()).to.eq('5', 'Expected featuredArticles7 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles8Input()).to.eq('5', 'Expected featuredArticles8 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles9Input()).to.eq('5', 'Expected featuredArticles9 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getFeaturedArticles10Input()).to.eq('5', 'Expected featuredArticles10 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getPopularNews1Input()).to.eq('5', 'Expected popularNews1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getPopularNews2Input()).to.eq('5', 'Expected popularNews2 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getPopularNews3Input()).to.eq('5', 'Expected popularNews3 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getPopularNews4Input()).to.eq('5', 'Expected popularNews4 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getPopularNews5Input()).to.eq('5', 'Expected popularNews5 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getPopularNews6Input()).to.eq('5', 'Expected popularNews6 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getPopularNews7Input()).to.eq('5', 'Expected popularNews7 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getPopularNews8Input()).to.eq('5', 'Expected popularNews8 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getWeeklyNews1Input()).to.eq('5', 'Expected weeklyNews1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getWeeklyNews2Input()).to.eq('5', 'Expected weeklyNews2 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getWeeklyNews3Input()).to.eq('5', 'Expected weeklyNews3 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getWeeklyNews4Input()).to.eq('5', 'Expected weeklyNews4 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getNewsFeeds1Input()).to.eq('5', 'Expected newsFeeds1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getNewsFeeds2Input()).to.eq('5', 'Expected newsFeeds2 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getNewsFeeds3Input()).to.eq('5', 'Expected newsFeeds3 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getNewsFeeds4Input()).to.eq('5', 'Expected newsFeeds4 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getNewsFeeds5Input()).to.eq('5', 'Expected newsFeeds5 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getNewsFeeds6Input()).to.eq('5', 'Expected newsFeeds6 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getUsefulLinks1Input()).to.eq('5', 'Expected usefulLinks1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getUsefulLinks2Input()).to.eq('5', 'Expected usefulLinks2 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getUsefulLinks3Input()).to.eq('5', 'Expected usefulLinks3 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getUsefulLinks4Input()).to.eq('5', 'Expected usefulLinks4 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getUsefulLinks5Input()).to.eq('5', 'Expected usefulLinks5 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getUsefulLinks6Input()).to.eq('5', 'Expected usefulLinks6 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentVideos1Input()).to.eq('5', 'Expected recentVideos1 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentVideos2Input()).to.eq('5', 'Expected recentVideos2 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentVideos3Input()).to.eq('5', 'Expected recentVideos3 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentVideos4Input()).to.eq('5', 'Expected recentVideos4 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentVideos5Input()).to.eq('5', 'Expected recentVideos5 value to be equals to 5');
    expect(await frontpageconfigUpdatePage.getRecentVideos6Input()).to.eq('5', 'Expected recentVideos6 value to be equals to 5');
    await frontpageconfigUpdatePage.save();
    expect(await frontpageconfigUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await frontpageconfigComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last Frontpageconfig', async () => {
    const nbButtonsBeforeDelete = await frontpageconfigComponentsPage.countDeleteButtons();
    await frontpageconfigComponentsPage.clickOnLastDeleteButton();

    frontpageconfigDeleteDialog = new FrontpageconfigDeleteDialog();
    expect(await frontpageconfigDeleteDialog.getDialogTitle()).to.eq('spingularApp.frontpageconfig.delete.question');
    await frontpageconfigDeleteDialog.clickOnConfirmButton();

    expect(await frontpageconfigComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
