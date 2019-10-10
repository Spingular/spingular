import { element, by, ElementFinder } from 'protractor';

export class TagComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tag div table .btn-danger'));
  title = element.all(by.css('jhi-tag div h2#page-heading span')).first();

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

export class TagUpdatePage {
  pageTitle = element(by.id('jhi-tag-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tagNameInput = element(by.id('field_tagName'));
  postSelect = element(by.id('field_post'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTagNameInput(tagName) {
    await this.tagNameInput.sendKeys(tagName);
  }

  async getTagNameInput() {
    return await this.tagNameInput.getAttribute('value');
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

export class TagDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tag-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tag'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
