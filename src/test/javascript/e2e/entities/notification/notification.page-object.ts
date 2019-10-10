import { element, by, ElementFinder } from 'protractor';

export class NotificationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-notification div table .btn-danger'));
  title = element.all(by.css('jhi-notification div h2#page-heading span')).first();

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

export class NotificationUpdatePage {
  pageTitle = element(by.id('jhi-notification-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  notificationDateInput = element(by.id('field_notificationDate'));
  notificationReasonSelect = element(by.id('field_notificationReason'));
  notificationTextInput = element(by.id('field_notificationText'));
  isDeliveredInput = element(by.id('field_isDelivered'));
  appuserSelect = element(by.id('field_appuser'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return await this.creationDateInput.getAttribute('value');
  }

  async setNotificationDateInput(notificationDate) {
    await this.notificationDateInput.sendKeys(notificationDate);
  }

  async getNotificationDateInput() {
    return await this.notificationDateInput.getAttribute('value');
  }

  async setNotificationReasonSelect(notificationReason) {
    await this.notificationReasonSelect.sendKeys(notificationReason);
  }

  async getNotificationReasonSelect() {
    return await this.notificationReasonSelect.element(by.css('option:checked')).getText();
  }

  async notificationReasonSelectLastOption(timeout?: number) {
    await this.notificationReasonSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setNotificationTextInput(notificationText) {
    await this.notificationTextInput.sendKeys(notificationText);
  }

  async getNotificationTextInput() {
    return await this.notificationTextInput.getAttribute('value');
  }

  getIsDeliveredInput(timeout?: number) {
    return this.isDeliveredInput;
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

export class NotificationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-notification-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-notification'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
