import { element, by, ElementFinder } from 'protractor';

export class ProposalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-proposal div table .btn-danger'));
  title = element.all(by.css('jhi-proposal div h2#page-heading span')).first();

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

export class ProposalUpdatePage {
  pageTitle = element(by.id('jhi-proposal-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  proposalNameInput = element(by.id('field_proposalName'));
  proposalTypeSelect = element(by.id('field_proposalType'));
  proposalRoleSelect = element(by.id('field_proposalRole'));
  releaseDateInput = element(by.id('field_releaseDate'));
  isOpenInput = element(by.id('field_isOpen'));
  isAcceptedInput = element(by.id('field_isAccepted'));
  isPaidInput = element(by.id('field_isPaid'));
  appuserSelect = element(by.id('field_appuser'));
  postSelect = element(by.id('field_post'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return await this.creationDateInput.getAttribute('value');
  }

  async setProposalNameInput(proposalName) {
    await this.proposalNameInput.sendKeys(proposalName);
  }

  async getProposalNameInput() {
    return await this.proposalNameInput.getAttribute('value');
  }

  async setProposalTypeSelect(proposalType) {
    await this.proposalTypeSelect.sendKeys(proposalType);
  }

  async getProposalTypeSelect() {
    return await this.proposalTypeSelect.element(by.css('option:checked')).getText();
  }

  async proposalTypeSelectLastOption(timeout?: number) {
    await this.proposalTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setProposalRoleSelect(proposalRole) {
    await this.proposalRoleSelect.sendKeys(proposalRole);
  }

  async getProposalRoleSelect() {
    return await this.proposalRoleSelect.element(by.css('option:checked')).getText();
  }

  async proposalRoleSelectLastOption(timeout?: number) {
    await this.proposalRoleSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setReleaseDateInput(releaseDate) {
    await this.releaseDateInput.sendKeys(releaseDate);
  }

  async getReleaseDateInput() {
    return await this.releaseDateInput.getAttribute('value');
  }

  getIsOpenInput(timeout?: number) {
    return this.isOpenInput;
  }
  getIsAcceptedInput(timeout?: number) {
    return this.isAcceptedInput;
  }
  getIsPaidInput(timeout?: number) {
    return this.isPaidInput;
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

  async postSelectLastOption(timeout?: number) {
    await this.postSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async postSelectOption(option) {
    await this.postSelect.sendKeys(option);
  }

  getPostSelect(): ElementFinder {
    return this.postSelect;
  }

  async getPostSelectedOption() {
    return await this.postSelect.element(by.css('option:checked')).getText();
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

export class ProposalDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-proposal-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-proposal'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
