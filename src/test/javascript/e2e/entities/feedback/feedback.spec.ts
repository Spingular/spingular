// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FeedbackComponentsPage, FeedbackDeleteDialog, FeedbackUpdatePage } from './feedback.page-object';

const expect = chai.expect;

describe('Feedback e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let feedbackComponentsPage: FeedbackComponentsPage;
  let feedbackUpdatePage: FeedbackUpdatePage;
  let feedbackDeleteDialog: FeedbackDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Feedbacks', async () => {
    await navBarPage.goToEntity('feedback');
    feedbackComponentsPage = new FeedbackComponentsPage();
    await browser.wait(ec.visibilityOf(feedbackComponentsPage.title), 5000);
    expect(await feedbackComponentsPage.getTitle()).to.eq('spingularApp.feedback.home.title');
  });

  it('should load create Feedback page', async () => {
    await feedbackComponentsPage.clickOnCreateButton();
    feedbackUpdatePage = new FeedbackUpdatePage();
    expect(await feedbackUpdatePage.getPageTitle()).to.eq('spingularApp.feedback.home.createOrEditLabel');
    await feedbackUpdatePage.cancel();
  });

  it('should create and save Feedbacks', async () => {
    const nbButtonsBeforeCreate = await feedbackComponentsPage.countDeleteButtons();

    await feedbackComponentsPage.clickOnCreateButton();
    await promise.all([
      feedbackUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      feedbackUpdatePage.setNameInput('name'),
      feedbackUpdatePage.setEmailInput('email'),
      feedbackUpdatePage.setFeedbackInput('feedback')
    ]);
    expect(await feedbackUpdatePage.getCreationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationDate value to be equals to 2000-12-31'
    );
    expect(await feedbackUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await feedbackUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await feedbackUpdatePage.getFeedbackInput()).to.eq('feedback', 'Expected Feedback value to be equals to feedback');
    await feedbackUpdatePage.save();
    expect(await feedbackUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await feedbackComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Feedback', async () => {
    const nbButtonsBeforeDelete = await feedbackComponentsPage.countDeleteButtons();
    await feedbackComponentsPage.clickOnLastDeleteButton();

    feedbackDeleteDialog = new FeedbackDeleteDialog();
    expect(await feedbackDeleteDialog.getDialogTitle()).to.eq('spingularApp.feedback.delete.question');
    await feedbackDeleteDialog.clickOnConfirmButton();

    expect(await feedbackComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
