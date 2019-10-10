import { element, by, ElementFinder } from 'protractor';

export class MessageComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-message div table .btn-danger'));
  title = element.all(by.css('jhi-message div h2#page-heading span')).first();

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

export class MessageUpdatePage {
  pageTitle = element(by.id('jhi-message-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  messageTextInput = element(by.id('field_messageText'));
  isDeliveredInput = element(by.id('field_isDelivered'));
  senderSelect = element(by.id('field_sender'));
  receiverSelect = element(by.id('field_receiver'));

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

  async senderSelectLastOption(timeout?: number) {
    await this.senderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async senderSelectOption(option) {
    await this.senderSelect.sendKeys(option);
  }

  getSenderSelect(): ElementFinder {
    return this.senderSelect;
  }

  async getSenderSelectedOption() {
    return await this.senderSelect.element(by.css('option:checked')).getText();
  }

  async receiverSelectLastOption(timeout?: number) {
    await this.receiverSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async receiverSelectOption(option) {
    await this.receiverSelect.sendKeys(option);
  }

  getReceiverSelect(): ElementFinder {
    return this.receiverSelect;
  }

  async getReceiverSelectedOption() {
    return await this.receiverSelect.element(by.css('option:checked')).getText();
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

export class MessageDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-message-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-message'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
