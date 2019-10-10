import { element, by, ElementFinder } from 'protractor';

export class CommunityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-community div table .btn-danger'));
  title = element.all(by.css('jhi-community div h2#page-heading span')).first();

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

export class CommunityUpdatePage {
  pageTitle = element(by.id('jhi-community-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  communityNameInput = element(by.id('field_communityName'));
  communityDescriptionInput = element(by.id('field_communityDescription'));
  imageInput = element(by.id('file_image'));
  isActiveInput = element(by.id('field_isActive'));
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

  async setCommunityNameInput(communityName) {
    await this.communityNameInput.sendKeys(communityName);
  }

  async getCommunityNameInput() {
    return await this.communityNameInput.getAttribute('value');
  }

  async setCommunityDescriptionInput(communityDescription) {
    await this.communityDescriptionInput.sendKeys(communityDescription);
  }

  async getCommunityDescriptionInput() {
    return await this.communityDescriptionInput.getAttribute('value');
  }

  async setImageInput(image) {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput() {
    return await this.imageInput.getAttribute('value');
  }

  getIsActiveInput(timeout?: number) {
    return this.isActiveInput;
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

export class CommunityDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-community-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-community'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
