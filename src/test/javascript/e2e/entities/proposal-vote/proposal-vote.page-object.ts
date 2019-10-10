import { element, by, ElementFinder } from 'protractor';

export class ProposalVoteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-proposal-vote div table .btn-danger'));
  title = element.all(by.css('jhi-proposal-vote div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ProposalVoteUpdatePage {
  pageTitle = element(by.id('jhi-proposal-vote-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  votePointsInput = element(by.id('field_votePoints'));
  appuserSelect = element(by.id('field_appuser'));
  proposalSelect = element(by.id('field_proposal'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return await this.creationDateInput.getAttribute('value');
  }

  async setVotePointsInput(votePoints) {
    await this.votePointsInput.sendKeys(votePoints);
  }

  async getVotePointsInput() {
    return await this.votePointsInput.getAttribute('value');
  }

  async appuserSelectLastOption(timeout?: number) {
    await this.appuserSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async appuserSelectOption(option) {
    await this.appuserSelect.sendKeys(option);
  }

  getAppuserSelect(): ElementFinder {
    return this.appuserSelect;
  }

  async getAppuserSelectedOption() {
    return await this.appuserSelect.element(by.css('option:checked')).getText();
  }

  async proposalSelectLastOption(timeout?: number) {
    await this.proposalSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async proposalSelectOption(option) {
    await this.proposalSelect.sendKeys(option);
  }

  getProposalSelect(): ElementFinder {
    return this.proposalSelect;
  }

  async getProposalSelectedOption() {
    return await this.proposalSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProposalVoteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-proposalVote-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-proposalVote'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
