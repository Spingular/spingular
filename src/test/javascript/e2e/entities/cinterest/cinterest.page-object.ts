import { element, by, ElementFinder } from 'protractor';

export class CinterestComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cinterest div table .btn-danger'));
  title = element.all(by.css('jhi-cinterest div h2#page-heading span')).first();

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

export class CinterestUpdatePage {
  pageTitle = element(by.id('jhi-cinterest-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  interestNameInput = element(by.id('field_interestName'));
  communitySelect = element(by.id('field_community'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setInterestNameInput(interestName) {
    await this.interestNameInput.sendKeys(interestName);
  }

  async getInterestNameInput() {
    return await this.interestNameInput.getAttribute('value');
  }

  async communitySelectLastOption(timeout?: number) {
    await this.communitySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async communitySelectOption(option) {
    await this.communitySelect.sendKeys(option);
  }

  getCommunitySelect(): ElementFinder {
    return this.communitySelect;
  }

  async getCommunitySelectedOption() {
    return await this.communitySelect.element(by.css('option:checked')).getText();
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

export class CinterestDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cinterest-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cinterest'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
