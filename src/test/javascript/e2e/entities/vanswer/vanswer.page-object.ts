import { element, by, ElementFinder } from 'protractor';

export class VanswerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-vanswer div table .btn-danger'));
  title = element.all(by.css('jhi-vanswer div h2#page-heading span')).first();

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

export class VanswerUpdatePage {
  pageTitle = element(by.id('jhi-vanswer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  urlVanswerInput = element(by.id('field_urlVanswer'));
  acceptedInput = element(by.id('field_accepted'));
  appuserSelect = element(by.id('field_appuser'));
  vquestionSelect = element(by.id('field_vquestion'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return await this.creationDateInput.getAttribute('value');
  }

  async setUrlVanswerInput(urlVanswer) {
    await this.urlVanswerInput.sendKeys(urlVanswer);
  }

  async getUrlVanswerInput() {
    return await this.urlVanswerInput.getAttribute('value');
  }

  getAcceptedInput(timeout?: number) {
    return this.acceptedInput;
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

  async vquestionSelectLastOption(timeout?: number) {
    await this.vquestionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async vquestionSelectOption(option) {
    await this.vquestionSelect.sendKeys(option);
  }

  getVquestionSelect(): ElementFinder {
    return this.vquestionSelect;
  }

  async getVquestionSelectedOption() {
    return await this.vquestionSelect.element(by.css('option:checked')).getText();
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

export class VanswerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-vanswer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-vanswer'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
