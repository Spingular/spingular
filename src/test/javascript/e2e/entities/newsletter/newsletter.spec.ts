// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { NewsletterComponentsPage, NewsletterDeleteDialog, NewsletterUpdatePage } from './newsletter.page-object';

const expect = chai.expect;

describe('Newsletter e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let newsletterComponentsPage: NewsletterComponentsPage;
  let newsletterUpdatePage: NewsletterUpdatePage;
  let newsletterDeleteDialog: NewsletterDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Newsletters', async () => {
    await navBarPage.goToEntity('newsletter');
    newsletterComponentsPage = new NewsletterComponentsPage();
    await browser.wait(ec.visibilityOf(newsletterComponentsPage.title), 5000);
    expect(await newsletterComponentsPage.getTitle()).to.eq('spingularApp.newsletter.home.title');
  });

  it('should load create Newsletter page', async () => {
    await newsletterComponentsPage.clickOnCreateButton();
    newsletterUpdatePage = new NewsletterUpdatePage();
    expect(await newsletterUpdatePage.getPageTitle()).to.eq('spingularApp.newsletter.home.createOrEditLabel');
    await newsletterUpdatePage.cancel();
  });

  it('should create and save Newsletters', async () => {
    const nbButtonsBeforeCreate = await newsletterComponentsPage.countDeleteButtons();

    await newsletterComponentsPage.clickOnCreateButton();
    await promise.all([
      newsletterUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      newsletterUpdatePage.setEmailInput('email')
    ]);
    expect(await newsletterUpdatePage.getCreationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationDate value to be equals to 2000-12-31'
    );
    expect(await newsletterUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    await newsletterUpdatePage.save();
    expect(await newsletterUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await newsletterComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Newsletter', async () => {
    const nbButtonsBeforeDelete = await newsletterComponentsPage.countDeleteButtons();
    await newsletterComponentsPage.clickOnLastDeleteButton();

    newsletterDeleteDialog = new NewsletterDeleteDialog();
    expect(await newsletterDeleteDialog.getDialogTitle()).to.eq('spingularApp.newsletter.delete.question');
    await newsletterDeleteDialog.clickOnConfirmButton();

    expect(await newsletterComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
