// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  AppphotoComponentsPage,
  /* AppphotoDeleteDialog,
   */ AppphotoUpdatePage
} from './appphoto.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Appphoto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let appphotoComponentsPage: AppphotoComponentsPage;
  let appphotoUpdatePage: AppphotoUpdatePage;
  /* let appphotoDeleteDialog: AppphotoDeleteDialog; */
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Appphotos', async () => {
    await navBarPage.goToEntity('appphoto');
    appphotoComponentsPage = new AppphotoComponentsPage();
    await browser.wait(ec.visibilityOf(appphotoComponentsPage.title), 5000);
    expect(await appphotoComponentsPage.getTitle()).to.eq('spingularApp.appphoto.home.title');
  });

  it('should load create Appphoto page', async () => {
    await appphotoComponentsPage.clickOnCreateButton();
    appphotoUpdatePage = new AppphotoUpdatePage();
    expect(await appphotoUpdatePage.getPageTitle()).to.eq('spingularApp.appphoto.home.createOrEditLabel');
    await appphotoUpdatePage.cancel();
  });

  /*  it('should create and save Appphotos', async () => {
        const nbButtonsBeforeCreate = await appphotoComponentsPage.countDeleteButtons();

        await appphotoComponentsPage.clickOnCreateButton();
        await promise.all([
            appphotoUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            appphotoUpdatePage.setImageInput(absolutePath),
            appphotoUpdatePage.appuserSelectLastOption(),
        ]);
        expect(await appphotoUpdatePage.getCreationDateInput()).to.contain('2001-01-01T02:30', 'Expected creationDate value to be equals to 2000-12-31');
        expect(await appphotoUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
        await appphotoUpdatePage.save();
        expect(await appphotoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await appphotoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Appphoto', async () => {
        const nbButtonsBeforeDelete = await appphotoComponentsPage.countDeleteButtons();
        await appphotoComponentsPage.clickOnLastDeleteButton();

        appphotoDeleteDialog = new AppphotoDeleteDialog();
        expect(await appphotoDeleteDialog.getDialogTitle())
            .to.eq('spingularApp.appphoto.delete.question');
        await appphotoDeleteDialog.clickOnConfirmButton();

        expect(await appphotoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
