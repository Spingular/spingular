// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  AppprofileComponentsPage,
  /* AppprofileDeleteDialog,
   */ AppprofileUpdatePage
} from './appprofile.page-object';

const expect = chai.expect;

describe('Appprofile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let appprofileComponentsPage: AppprofileComponentsPage;
  let appprofileUpdatePage: AppprofileUpdatePage;
  /* let appprofileDeleteDialog: AppprofileDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Appprofiles', async () => {
    await navBarPage.goToEntity('appprofile');
    appprofileComponentsPage = new AppprofileComponentsPage();
    await browser.wait(ec.visibilityOf(appprofileComponentsPage.title), 5000);
    expect(await appprofileComponentsPage.getTitle()).to.eq('spingularApp.appprofile.home.title');
  });

  it('should load create Appprofile page', async () => {
    await appprofileComponentsPage.clickOnCreateButton();
    appprofileUpdatePage = new AppprofileUpdatePage();
    expect(await appprofileUpdatePage.getPageTitle()).to.eq('spingularApp.appprofile.home.createOrEditLabel');
    await appprofileUpdatePage.cancel();
  });

  /*  it('should create and save Appprofiles', async () => {
        const nbButtonsBeforeCreate = await appprofileComponentsPage.countDeleteButtons();

        await appprofileComponentsPage.clickOnCreateButton();
        await promise.all([
            appprofileUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            appprofileUpdatePage.genderSelectLastOption(),
            appprofileUpdatePage.setPhoneInput('phone'),
            appprofileUpdatePage.setBioInput('bio'),
            appprofileUpdatePage.setFacebookInput('facebook'),
            appprofileUpdatePage.setTwitterInput('twitter'),
            appprofileUpdatePage.setLinkedinInput('linkedin'),
            appprofileUpdatePage.setInstagramInput('instagram'),
            appprofileUpdatePage.setGooglePlusInput('googlePlus'),
            appprofileUpdatePage.setBirthdateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            appprofileUpdatePage.civilStatusSelectLastOption(),
            appprofileUpdatePage.lookingForSelectLastOption(),
            appprofileUpdatePage.purposeSelectLastOption(),
            appprofileUpdatePage.physicalSelectLastOption(),
            appprofileUpdatePage.religionSelectLastOption(),
            appprofileUpdatePage.ethnicGroupSelectLastOption(),
            appprofileUpdatePage.studiesSelectLastOption(),
            appprofileUpdatePage.setSibblingsInput('5'),
            appprofileUpdatePage.eyesSelectLastOption(),
            appprofileUpdatePage.smokerSelectLastOption(),
            appprofileUpdatePage.childrenSelectLastOption(),
            appprofileUpdatePage.futureChildrenSelectLastOption(),
            appprofileUpdatePage.appuserSelectLastOption(),
        ]);
        expect(await appprofileUpdatePage.getCreationDateInput()).to.contain('2001-01-01T02:30', 'Expected creationDate value to be equals to 2000-12-31');
        expect(await appprofileUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');
        expect(await appprofileUpdatePage.getBioInput()).to.eq('bio', 'Expected Bio value to be equals to bio');
        expect(await appprofileUpdatePage.getFacebookInput()).to.eq('facebook', 'Expected Facebook value to be equals to facebook');
        expect(await appprofileUpdatePage.getTwitterInput()).to.eq('twitter', 'Expected Twitter value to be equals to twitter');
        expect(await appprofileUpdatePage.getLinkedinInput()).to.eq('linkedin', 'Expected Linkedin value to be equals to linkedin');
        expect(await appprofileUpdatePage.getInstagramInput()).to.eq('instagram', 'Expected Instagram value to be equals to instagram');
        expect(await appprofileUpdatePage.getGooglePlusInput()).to.eq('googlePlus', 'Expected GooglePlus value to be equals to googlePlus');
        expect(await appprofileUpdatePage.getBirthdateInput()).to.contain('2001-01-01T02:30', 'Expected birthdate value to be equals to 2000-12-31');
        expect(await appprofileUpdatePage.getSibblingsInput()).to.eq('5', 'Expected sibblings value to be equals to 5');
        const selectedPet = appprofileUpdatePage.getPetInput();
        if (await selectedPet.isSelected()) {
            await appprofileUpdatePage.getPetInput().click();
            expect(await appprofileUpdatePage.getPetInput().isSelected(), 'Expected pet not to be selected').to.be.false;
        } else {
            await appprofileUpdatePage.getPetInput().click();
            expect(await appprofileUpdatePage.getPetInput().isSelected(), 'Expected pet to be selected').to.be.true;
        }
        await appprofileUpdatePage.save();
        expect(await appprofileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await appprofileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Appprofile', async () => {
        const nbButtonsBeforeDelete = await appprofileComponentsPage.countDeleteButtons();
        await appprofileComponentsPage.clickOnLastDeleteButton();

        appprofileDeleteDialog = new AppprofileDeleteDialog();
        expect(await appprofileDeleteDialog.getDialogTitle())
            .to.eq('spingularApp.appprofile.delete.question');
        await appprofileDeleteDialog.clickOnConfirmButton();

        expect(await appprofileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
