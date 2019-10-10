// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  CommunityComponentsPage,
  /* CommunityDeleteDialog,
   */ CommunityUpdatePage
} from './community.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Community e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let communityComponentsPage: CommunityComponentsPage;
  let communityUpdatePage: CommunityUpdatePage;
  /* let communityDeleteDialog: CommunityDeleteDialog; */
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

  it('should load Communities', async () => {
    await navBarPage.goToEntity('community');
    communityComponentsPage = new CommunityComponentsPage();
    await browser.wait(ec.visibilityOf(communityComponentsPage.title), 5000);
    expect(await communityComponentsPage.getTitle()).to.eq('spingularApp.community.home.title');
  });

  it('should load create Community page', async () => {
    await communityComponentsPage.clickOnCreateButton();
    communityUpdatePage = new CommunityUpdatePage();
    expect(await communityUpdatePage.getPageTitle()).to.eq('spingularApp.community.home.createOrEditLabel');
    await communityUpdatePage.cancel();
  });

  /*  it('should create and save Communities', async () => {
        const nbButtonsBeforeCreate = await communityComponentsPage.countDeleteButtons();

        await communityComponentsPage.clickOnCreateButton();
        await promise.all([
            communityUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            communityUpdatePage.setCommunityNameInput('communityName'),
            communityUpdatePage.setCommunityDescriptionInput('communityDescription'),
            communityUpdatePage.setImageInput(absolutePath),
            communityUpdatePage.appuserSelectLastOption(),
        ]);
        expect(await communityUpdatePage.getCreationDateInput()).to.contain('2001-01-01T02:30', 'Expected creationDate value to be equals to 2000-12-31');
        expect(await communityUpdatePage.getCommunityNameInput()).to.eq('communityName', 'Expected CommunityName value to be equals to communityName');
        expect(await communityUpdatePage.getCommunityDescriptionInput()).to.eq('communityDescription', 'Expected CommunityDescription value to be equals to communityDescription');
        expect(await communityUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
        const selectedIsActive = communityUpdatePage.getIsActiveInput();
        if (await selectedIsActive.isSelected()) {
            await communityUpdatePage.getIsActiveInput().click();
            expect(await communityUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive not to be selected').to.be.false;
        } else {
            await communityUpdatePage.getIsActiveInput().click();
            expect(await communityUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive to be selected').to.be.true;
        }
        await communityUpdatePage.save();
        expect(await communityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await communityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Community', async () => {
        const nbButtonsBeforeDelete = await communityComponentsPage.countDeleteButtons();
        await communityComponentsPage.clickOnLastDeleteButton();

        communityDeleteDialog = new CommunityDeleteDialog();
        expect(await communityDeleteDialog.getDialogTitle())
            .to.eq('spingularApp.community.delete.question');
        await communityDeleteDialog.clickOnConfirmButton();

        expect(await communityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
