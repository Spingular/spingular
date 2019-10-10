// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ProposalVoteComponentsPage, ProposalVoteDeleteDialog, ProposalVoteUpdatePage } from './proposal-vote.page-object';

const expect = chai.expect;

describe('ProposalVote e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let proposalVoteComponentsPage: ProposalVoteComponentsPage;
  let proposalVoteUpdatePage: ProposalVoteUpdatePage;
  let proposalVoteDeleteDialog: ProposalVoteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProposalVotes', async () => {
    await navBarPage.goToEntity('proposal-vote');
    proposalVoteComponentsPage = new ProposalVoteComponentsPage();
    await browser.wait(ec.visibilityOf(proposalVoteComponentsPage.title), 5000);
    expect(await proposalVoteComponentsPage.getTitle()).to.eq('spingularApp.proposalVote.home.title');
  });

  it('should load create ProposalVote page', async () => {
    await proposalVoteComponentsPage.clickOnCreateButton();
    proposalVoteUpdatePage = new ProposalVoteUpdatePage();
    expect(await proposalVoteUpdatePage.getPageTitle()).to.eq('spingularApp.proposalVote.home.createOrEditLabel');
    await proposalVoteUpdatePage.cancel();
  });

  it('should create and save ProposalVotes', async () => {
    const nbButtonsBeforeCreate = await proposalVoteComponentsPage.countDeleteButtons();

    await proposalVoteComponentsPage.clickOnCreateButton();
    await promise.all([
      proposalVoteUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      proposalVoteUpdatePage.setVotePointsInput('5'),
      proposalVoteUpdatePage.appuserSelectLastOption(),
      proposalVoteUpdatePage.proposalSelectLastOption()
    ]);
    expect(await proposalVoteUpdatePage.getCreationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationDate value to be equals to 2000-12-31'
    );
    expect(await proposalVoteUpdatePage.getVotePointsInput()).to.eq('5', 'Expected votePoints value to be equals to 5');
    await proposalVoteUpdatePage.save();
    expect(await proposalVoteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await proposalVoteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ProposalVote', async () => {
    const nbButtonsBeforeDelete = await proposalVoteComponentsPage.countDeleteButtons();
    await proposalVoteComponentsPage.clickOnLastDeleteButton();

    proposalVoteDeleteDialog = new ProposalVoteDeleteDialog();
    expect(await proposalVoteDeleteDialog.getDialogTitle()).to.eq('spingularApp.proposalVote.delete.question');
    await proposalVoteDeleteDialog.clickOnConfirmButton();

    expect(await proposalVoteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
