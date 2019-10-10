import { element, by, ElementFinder } from 'protractor';

export class CmessageComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cmessage div table .btn-danger'));
  title = element.all(by.css('jhi-cmessage div h2#page-heading span')).first();

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

export class CmessageUpdatePage {
  pageTitle = element(by.id('jhi-cmessage-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  messageTextInput = element(by.id('field_messageText'));
  isDeliveredInput = element(by.id('field_isDelivered'));
  csenderSelect = element(by.id('field_csender'));
  creceiverSelect = element(by.id('field_creceiver'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return await this.creationDateInput.getAttribute('value');
  }

  async setMessageTextInput(messageText) {
    await this.messageTextInput.sendKeys(messageText);
  }

  async getMessageTextInput() {
    return await this.messageTextInput.getAttribute('value');
  }

  getIsDeliveredInput(timeout?: number) {
    return this.isDeliveredInput;
  }

  async csenderSelectLastOption(timeout?: number) {
    await this.csenderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async csenderSelectOption(option) {
    await this.csenderSelect.sendKeys(option);
  }

  getCsenderSelect(): ElementFinder {
    return this.csenderSelect;
  }

  async getCsenderSelectedOption() {
    return await this.csenderSelect.element(by.css('option:checked')).getText();
  }

  async creceiverSelectLastOption(timeout?: number) {
    await this.creceiverSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async creceiverSelectOption(option) {
    await this.creceiverSelect.sendKeys(option);
  }

  getCreceiverSelect(): ElementFinder {
    return this.creceiverSelect;
  }

  async getCreceiverSelectedOption() {
    return await this.creceiverSelect.element(by.css('option:checked')).getText();
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

export class CmessageDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cmessage-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cmessage'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
