// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ProposalComponentsPage, ProposalDeleteDialog, ProposalUpdatePage } from './proposal.page-object';

const expect = chai.expect;

describe('Proposal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let proposalComponentsPage: ProposalComponentsPage;
  let proposalUpdatePage: ProposalUpdatePage;
  let proposalDeleteDialog: ProposalDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Proposals', async () => {
    await navBarPage.goToEntity('proposal');
    proposalComponentsPage = new ProposalComponentsPage();
    await browser.wait(ec.visibilityOf(proposalComponentsPage.title), 5000);
    expect(await proposalComponentsPage.getTitle()).to.eq('spingularApp.proposal.home.title');
  });

  it('should load create Proposal page', async () => {
    await proposalComponentsPage.clickOnCreateButton();
    proposalUpdatePage = new ProposalUpdatePage();
    expect(await proposalUpdatePage.getPageTitle()).to.eq('spingularApp.proposal.home.createOrEditLabel');
    await proposalUpdatePage.cancel();
  });

  it('should create and save Proposals', async () => {
    const nbButtonsBeforeCreate = await proposalComponentsPage.countDeleteButtons();

    await proposalComponentsPage.clickOnCreateButton();
    await promise.all([
      proposalUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      proposalUpdatePage.setProposalNameInput('proposalName'),
      proposalUpdatePage.proposalTypeSelectLastOption(),
      proposalUpdatePage.proposalRoleSelectLastOption(),
      proposalUpdatePage.setReleaseDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      proposalUpdatePage.appuserSelectLastOption(),
      proposalUpdatePage.postSelectLastOption()
    ]);
    expect(await proposalUpdatePage.getCreationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationDate value to be equals to 2000-12-31'
    );
    expect(await proposalUpdatePage.getProposalNameInput()).to.eq(
      'proposalName',
      'Expected ProposalName value to be equals to proposalName'
    );
    expect(await proposalUpdatePage.getReleaseDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected releaseDate value to be equals to 2000-12-31'
    );
    const selectedIsOpen = proposalUpdatePage.getIsOpenInput();
    if (await selectedIsOpen.isSelected()) {
      await proposalUpdatePage.getIsOpenInput().click();
      expect(await proposalUpdatePage.getIsOpenInput().isSelected(), 'Expected isOpen not to be selected').to.be.false;
    } else {
      await proposalUpdatePage.getIsOpenInput().click();
      expect(await proposalUpdatePage.getIsOpenInput().isSelected(), 'Expected isOpen to be selected').to.be.true;
    }
    const selectedIsAccepted = proposalUpdatePage.getIsAcceptedInput();
    if (await selectedIsAccepted.isSelected()) {
      await proposalUpdatePage.getIsAcceptedInput().click();
      expect(await proposalUpdatePage.getIsAcceptedInput().isSelected(), 'Expected isAccepted not to be selected').to.be.false;
    } else {
      await proposalUpdatePage.getIsAcceptedInput().click();
      expect(await proposalUpdatePage.getIsAcceptedInput().isSelected(), 'Expected isAccepted to be selected').to.be.true;
    }
    const selectedIsPaid = proposalUpdatePage.getIsPaidInput();
    if (await selectedIsPaid.isSelected()) {
      await proposalUpdatePage.getIsPaidInput().click();
      expect(await proposalUpdatePage.getIsPaidInput().isSelected(), 'Expected isPaid not to be selected').to.be.false;
    } else {
      await proposalUpdatePage.getIsPaidInput().click();
      expect(await proposalUpdatePage.getIsPaidInput().isSelected(), 'Expected isPaid to be selected').to.be.true;
    }
    await proposalUpdatePage.save();
    expect(await proposalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await proposalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Proposal', async () => {
    const nbButtonsBeforeDelete = await proposalComponentsPage.countDeleteButtons();
    await proposalComponentsPage.clickOnLastDeleteButton();

    proposalDeleteDialog = new ProposalDeleteDialog();
    expect(await proposalDeleteDialog.getDialogTitle()).to.eq('spingularApp.proposal.delete.question');
    await proposalDeleteDialog.clickOnConfirmButton();

    expect(await proposalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
