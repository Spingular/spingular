import { element, by, ElementFinder } from 'protractor';

export class VtopicComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-vtopic div table .btn-danger'));
  title = element.all(by.css('jhi-vtopic div h2#page-heading span')).first();

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

export class VtopicUpdatePage {
  pageTitle = element(by.id('jhi-vtopic-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  vtopicTitleInput = element(by.id('field_vtopicTitle'));
  vtopicDescriptionInput = element(by.id('field_vtopicDescription'));
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

  async setVtopicTitleInput(vtopicTitle) {
    await this.vtopicTitleInput.sendKeys(vtopicTitle);
  }

  async getVtopicTitleInput() {
    return await this.vtopicTitleInput.getAttribute('value');
  }

  async setVtopicDescriptionInput(vtopicDescription) {
    await this.vtopicDescriptionInput.sendKeys(vtopicDescription);
  }

  async getVtopicDescriptionInput() {
    return await this.vtopicDescriptionInput.getAttribute('value');
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

export class VtopicDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-vtopic-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-vtopic'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
