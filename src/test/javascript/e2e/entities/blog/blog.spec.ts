// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  BlogComponentsPage,
  /* BlogDeleteDialog,
   */ BlogUpdatePage
} from './blog.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Blog e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let blogComponentsPage: BlogComponentsPage;
  let blogUpdatePage: BlogUpdatePage;
  /* let blogDeleteDialog: BlogDeleteDialog; */
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

  it('should load Blogs', async () => {
    await navBarPage.goToEntity('blog');
    blogComponentsPage = new BlogComponentsPage();
    await browser.wait(ec.visibilityOf(blogComponentsPage.title), 5000);
    expect(await blogComponentsPage.getTitle()).to.eq('spingularApp.blog.home.title');
  });

  it('should load create Blog page', async () => {
    await blogComponentsPage.clickOnCreateButton();
    blogUpdatePage = new BlogUpdatePage();
    expect(await blogUpdatePage.getPageTitle()).to.eq('spingularApp.blog.home.createOrEditLabel');
    await blogUpdatePage.cancel();
  });

  /*  it('should create and save Blogs', async () => {
        const nbButtonsBeforeCreate = await blogComponentsPage.countDeleteButtons();

        await blogComponentsPage.clickOnCreateButton();
        await promise.all([
            blogUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            blogUpdatePage.setTitleInput('title'),
            blogUpdatePage.setImageInput(absolutePath),
            blogUpdatePage.appuserSelectLastOption(),
            blogUpdatePage.communitySelectLastOption(),
        ]);
        expect(await blogUpdatePage.getCreationDateInput()).to.contain('2001-01-01T02:30', 'Expected creationDate value to be equals to 2000-12-31');
        expect(await blogUpdatePage.getTitleInput()).to.eq('title', 'Expected Title value to be equals to title');
        expect(await blogUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
        await blogUpdatePage.save();
        expect(await blogUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await blogComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Blog', async () => {
        const nbButtonsBeforeDelete = await blogComponentsPage.countDeleteButtons();
        await blogComponentsPage.clickOnLastDeleteButton();

        blogDeleteDialog = new BlogDeleteDialog();
        expect(await blogDeleteDialog.getDialogTitle())
            .to.eq('spingularApp.blog.delete.question');
        await blogDeleteDialog.clickOnConfirmButton();

        expect(await blogComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
