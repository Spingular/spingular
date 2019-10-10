import { element, by, ElementFinder } from 'protractor';

export class FeedbackComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-feedback div table .btn-danger'));
  title = element.all(by.css('jhi-feedback div h2#page-heading span')).first();

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

export class FeedbackUpdatePage {
  pageTitle = element(by.id('jhi-feedback-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  nameInput = element(by.id('field_name'));
  emailInput = element(by.id('field_email'));
  feedbackInput = element(by.id('field_feedback'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return await this.creationDateInput.getAttribute('value');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
  }

  async setFeedbackInput(feedback) {
    await this.feedbackInput.sendKeys(feedback);
  }

  async getFeedbackInput() {
    return await this.feedbackInput.getAttribute('value');
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

export class FeedbackDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-feedback-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-feedback'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
