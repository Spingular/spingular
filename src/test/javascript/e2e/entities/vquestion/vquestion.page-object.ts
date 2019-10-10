import { element, by, ElementFinder } from 'protractor';

export class VquestionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-vquestion div table .btn-danger'));
  title = element.all(by.css('jhi-vquestion div h2#page-heading span')).first();

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

export class VquestionUpdatePage {
  pageTitle = element(by.id('jhi-vquestion-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  vquestionInput = element(by.id('field_vquestion'));
  vquestionDescriptionInput = element(by.id('field_vquestionDescription'));
  appuserSelect = element(by.id('field_appuser'));
  vtopicSelect = element(by.id('field_vtopic'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return await this.creationDateInput.getAttribute('value');
  }

  async setVquestionInput(vquestion) {
    await this.vquestionInput.sendKeys(vquestion);
  }

  async getVquestionInput() {
    return await this.vquestionInput.getAttribute('value');
  }

  async setVquestionDescriptionInput(vquestionDescription) {
    await this.vquestionDescriptionInput.sendKeys(vquestionDescription);
  }

  async getVquestionDescriptionInput() {
    return await this.vquestionDescriptionInput.getAttribute('value');
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

  async vtopicSelectLastOption(timeout?: number) {
    await this.vtopicSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async vtopicSelectOption(option) {
    await this.vtopicSelect.sendKeys(option);
  }

  getVtopicSelect(): ElementFinder {
    return this.vtopicSelect;
  }

  async getVtopicSelectedOption() {
    return await this.vtopicSelect.element(by.css('option:checked')).getText();
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

export class VquestionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-vquestion-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-vquestion'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
