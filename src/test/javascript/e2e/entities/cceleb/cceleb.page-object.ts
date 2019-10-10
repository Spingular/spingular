import { element, by, ElementFinder } from 'protractor';

export class CcelebComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cceleb div table .btn-danger'));
  title = element.all(by.css('jhi-cceleb div h2#page-heading span')).first();

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

export class CcelebUpdatePage {
  pageTitle = element(by.id('jhi-cceleb-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  celebNameInput = element(by.id('field_celebName'));
  communitySelect = element(by.id('field_community'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCelebNameInput(celebName) {
    await this.celebNameInput.sendKeys(celebName);
  }

  async getCelebNameInput() {
    return await this.celebNameInput.getAttribute('value');
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

export class CcelebDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cceleb-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cceleb'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
