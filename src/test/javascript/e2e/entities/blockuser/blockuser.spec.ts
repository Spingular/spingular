// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { BlockuserComponentsPage, BlockuserDeleteDialog, BlockuserUpdatePage } from './blockuser.page-object';

const expect = chai.expect;

describe('Blockuser e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let blockuserComponentsPage: BlockuserComponentsPage;
  let blockuserUpdatePage: BlockuserUpdatePage;
  let blockuserDeleteDialog: BlockuserDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Blockusers', async () => {
    await navBarPage.goToEntity('blockuser');
    blockuserComponentsPage = new BlockuserComponentsPage();
    await browser.wait(ec.visibilityOf(blockuserComponentsPage.title), 5000);
    expect(await blockuserComponentsPage.getTitle()).to.eq('spingularApp.blockuser.home.title');
  });

  it('should load create Blockuser page', async () => {
    await blockuserComponentsPage.clickOnCreateButton();
    blockuserUpdatePage = new BlockuserUpdatePage();
    expect(await blockuserUpdatePage.getPageTitle()).to.eq('spingularApp.blockuser.home.createOrEditLabel');
    await blockuserUpdatePage.cancel();
  });

  it('should create and save Blockusers', async () => {
    const nbButtonsBeforeCreate = await blockuserComponentsPage.countDeleteButtons();

    await blockuserComponentsPage.clickOnCreateButton();
    await promise.all([
      blockuserUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      blockuserUpdatePage.blockeduserSelectLastOption(),
      blockuserUpdatePage.blockinguserSelectLastOption(),
      blockuserUpdatePage.cblockeduserSelectLastOption(),
      blockuserUpdatePage.cblockinguserSelectLastOption()
    ]);
    expect(await blockuserUpdatePage.getCreationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationDate value to be equals to 2000-12-31'
    );
    await blockuserUpdatePage.save();
    expect(await blockuserUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await blockuserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Blockuser', async () => {
    const nbButtonsBeforeDelete = await blockuserComponentsPage.countDeleteButtons();
    await blockuserComponentsPage.clickOnLastDeleteButton();

    blockuserDeleteDialog = new BlockuserDeleteDialog();
    expect(await blockuserDeleteDialog.getDialogTitle()).to.eq('spingularApp.blockuser.delete.question');
    await blockuserDeleteDialog.clickOnConfirmButton();

    expect(await blockuserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
