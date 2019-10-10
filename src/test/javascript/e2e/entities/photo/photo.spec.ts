// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { PhotoComponentsPage, PhotoDeleteDialog, PhotoUpdatePage } from './photo.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Photo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let photoComponentsPage: PhotoComponentsPage;
  let photoUpdatePage: PhotoUpdatePage;
  let photoDeleteDialog: PhotoDeleteDialog;
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

  it('should load Photos', async () => {
    await navBarPage.goToEntity('photo');
    photoComponentsPage = new PhotoComponentsPage();
    await browser.wait(ec.visibilityOf(photoComponentsPage.title), 5000);
    expect(await photoComponentsPage.getTitle()).to.eq('spingularApp.photo.home.title');
  });

  it('should load create Photo page', async () => {
    await photoComponentsPage.clickOnCreateButton();
    photoUpdatePage = new PhotoUpdatePage();
    expect(await photoUpdatePage.getPageTitle()).to.eq('spingularApp.photo.home.createOrEditLabel');
    await photoUpdatePage.cancel();
  });

  it('should create and save Photos', async () => {
    const nbButtonsBeforeCreate = await photoComponentsPage.countDeleteButtons();

    await photoComponentsPage.clickOnCreateButton();
    await promise.all([
      photoUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      photoUpdatePage.setImageInput(absolutePath),
      photoUpdatePage.albumSelectLastOption(),
      photoUpdatePage.calbumSelectLastOption()
    ]);
    expect(await photoUpdatePage.getCreationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationDate value to be equals to 2000-12-31'
    );
    expect(await photoUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
    await photoUpdatePage.save();
    expect(await photoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await photoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Photo', async () => {
    const nbButtonsBeforeDelete = await photoComponentsPage.countDeleteButtons();
    await photoComponentsPage.clickOnLastDeleteButton();

    photoDeleteDialog = new PhotoDeleteDialog();
    expect(await photoDeleteDialog.getDialogTitle()).to.eq('spingularApp.photo.delete.question');
    await photoDeleteDialog.clickOnConfirmButton();

    expect(await photoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
