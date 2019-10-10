// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  VtopicComponentsPage,
  /* VtopicDeleteDialog,
   */ VtopicUpdatePage
} from './vtopic.page-object';

const expect = chai.expect;

describe('Vtopic e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vtopicComponentsPage: VtopicComponentsPage;
  let vtopicUpdatePage: VtopicUpdatePage;
  /* let vtopicDeleteDialog: VtopicDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Vtopics', async () => {
    await navBarPage.goToEntity('vtopic');
    vtopicComponentsPage = new VtopicComponentsPage();
    await browser.wait(ec.visibilityOf(vtopicComponentsPage.title), 5000);
    expect(await vtopicComponentsPage.getTitle()).to.eq('spingularApp.vtopic.home.title');
  });

  it('should load create Vtopic page', async () => {
    await vtopicComponentsPage.clickOnCreateButton();
    vtopicUpdatePage = new VtopicUpdatePage();
    expect(await vtopicUpdatePage.getPageTitle()).to.eq('spingularApp.vtopic.home.createOrEditLabel');
    await vtopicUpdatePage.cancel();
  });

  /*  it('should create and save Vtopics', async () => {
        const nbButtonsBeforeCreate = await vtopicComponentsPage.countDeleteButtons();

        await vtopicComponentsPage.clickOnCreateButton();
        await promise.all([
            vtopicUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            vtopicUpdatePage.setVtopicTitleInput('vtopicTitle'),
            vtopicUpdatePage.setVtopicDescriptionInput('vtopicDescription'),
            vtopicUpdatePage.appuserSelectLastOption(),
        ]);
        expect(await vtopicUpdatePage.getCreationDateInput()).to.contain('2001-01-01T02:30', 'Expected creationDate value to be equals to 2000-12-31');
        expect(await vtopicUpdatePage.getVtopicTitleInput()).to.eq('vtopicTitle', 'Expected VtopicTitle value to be equals to vtopicTitle');
        expect(await vtopicUpdatePage.getVtopicDescriptionInput()).to.eq('vtopicDescription', 'Expected VtopicDescription value to be equals to vtopicDescription');
        await vtopicUpdatePage.save();
        expect(await vtopicUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await vtopicComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Vtopic', async () => {
        const nbButtonsBeforeDelete = await vtopicComponentsPage.countDeleteButtons();
        await vtopicComponentsPage.clickOnLastDeleteButton();

        vtopicDeleteDialog = new VtopicDeleteDialog();
        expect(await vtopicDeleteDialog.getDialogTitle())
            .to.eq('spingularApp.vtopic.delete.question');
        await vtopicDeleteDialog.clickOnConfirmButton();

        expect(await vtopicComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
