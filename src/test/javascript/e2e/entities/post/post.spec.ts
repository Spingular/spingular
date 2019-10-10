// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  PostComponentsPage,
  /* PostDeleteDialog,
   */ PostUpdatePage
} from './post.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Post e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let postComponentsPage: PostComponentsPage;
  let postUpdatePage: PostUpdatePage;
  /* let postDeleteDialog: PostDeleteDialog; */
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

  it('should load Posts', async () => {
    await navBarPage.goToEntity('post');
    postComponentsPage = new PostComponentsPage();
    await browser.wait(ec.visibilityOf(postComponentsPage.title), 5000);
    expect(await postComponentsPage.getTitle()).to.eq('spingularApp.post.home.title');
  });

  it('should load create Post page', async () => {
    await postComponentsPage.clickOnCreateButton();
    postUpdatePage = new PostUpdatePage();
    expect(await postUpdatePage.getPageTitle()).to.eq('spingularApp.post.home.createOrEditLabel');
    await postUpdatePage.cancel();
  });

  /*  it('should create and save Posts', async () => {
        const nbButtonsBeforeCreate = await postComponentsPage.countDeleteButtons();

        await postComponentsPage.clickOnCreateButton();
        await promise.all([
            postUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            postUpdatePage.setPublicationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            postUpdatePage.setHeadlineInput('headline'),
            postUpdatePage.setLeadtextInput('leadtext'),
            postUpdatePage.setBodytextInput('bodytext'),
            postUpdatePage.setQuoteInput('quote'),
            postUpdatePage.setConclusionInput('conclusion'),
            postUpdatePage.setLinkTextInput('linkText'),
            postUpdatePage.setLinkURLInput('linkURL'),
            postUpdatePage.setImageInput(absolutePath),
            postUpdatePage.appuserSelectLastOption(),
            postUpdatePage.blogSelectLastOption(),
        ]);
        expect(await postUpdatePage.getCreationDateInput()).to.contain('2001-01-01T02:30', 'Expected creationDate value to be equals to 2000-12-31');
        expect(await postUpdatePage.getPublicationDateInput()).to.contain('2001-01-01T02:30', 'Expected publicationDate value to be equals to 2000-12-31');
        expect(await postUpdatePage.getHeadlineInput()).to.eq('headline', 'Expected Headline value to be equals to headline');
        expect(await postUpdatePage.getLeadtextInput()).to.eq('leadtext', 'Expected Leadtext value to be equals to leadtext');
        expect(await postUpdatePage.getBodytextInput()).to.eq('bodytext', 'Expected Bodytext value to be equals to bodytext');
        expect(await postUpdatePage.getQuoteInput()).to.eq('quote', 'Expected Quote value to be equals to quote');
        expect(await postUpdatePage.getConclusionInput()).to.eq('conclusion', 'Expected Conclusion value to be equals to conclusion');
        expect(await postUpdatePage.getLinkTextInput()).to.eq('linkText', 'Expected LinkText value to be equals to linkText');
        expect(await postUpdatePage.getLinkURLInput()).to.eq('linkURL', 'Expected LinkURL value to be equals to linkURL');
        expect(await postUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
        await postUpdatePage.save();
        expect(await postUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await postComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Post', async () => {
        const nbButtonsBeforeDelete = await postComponentsPage.countDeleteButtons();
        await postComponentsPage.clickOnLastDeleteButton();

        postDeleteDialog = new PostDeleteDialog();
        expect(await postDeleteDialog.getDialogTitle())
            .to.eq('spingularApp.post.delete.question');
        await postDeleteDialog.clickOnConfirmButton();

        expect(await postComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
