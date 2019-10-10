import { element, by, ElementFinder } from 'protractor';

export class BlockuserComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-blockuser div table .btn-danger'));
  title = element.all(by.css('jhi-blockuser div h2#page-heading span')).first();

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

export class BlockuserUpdatePage {
  pageTitle = element(by.id('jhi-blockuser-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  blockeduserSelect = element(by.id('field_blockeduser'));
  blockinguserSelect = element(by.id('field_blockinguser'));
  cblockeduserSelect = element(by.id('field_cblockeduser'));
  cblockinguserSelect = element(by.id('field_cblockinguser'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return await this.creationDateInput.getAttribute('value');
  }

  async blockeduserSelectLastOption(timeout?: number) {
    await this.blockeduserSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async blockeduserSelectOption(option) {
    await this.blockeduserSelect.sendKeys(option);
  }

  getBlockeduserSelect(): ElementFinder {
    return this.blockeduserSelect;
  }

  async getBlockeduserSelectedOption() {
    return await this.blockeduserSelect.element(by.css('option:checked')).getText();
  }

  async blockinguserSelectLastOption(timeout?: number) {
    await this.blockinguserSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async blockinguserSelectOption(option) {
    await this.blockinguserSelect.sendKeys(option);
  }

  getBlockinguserSelect(): ElementFinder {
    return this.blockinguserSelect;
  }

  async getBlockinguserSelectedOption() {
    return await this.blockinguserSelect.element(by.css('option:checked')).getText();
  }

  async cblockeduserSelectLastOption(timeout?: number) {
    await this.cblockeduserSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async cblockeduserSelectOption(option) {
    await this.cblockeduserSelect.sendKeys(option);
  }

  getCblockeduserSelect(): ElementFinder {
    return this.cblockeduserSelect;
  }

  async getCblockeduserSelectedOption() {
    return await this.cblockeduserSelect.element(by.css('option:checked')).getText();
  }

  async cblockinguserSelectLastOption(timeout?: number) {
    await this.cblockinguserSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async cblockinguserSelectOption(option) {
    await this.cblockinguserSelect.sendKeys(option);
  }

  getCblockinguserSelect(): ElementFinder {
    return this.cblockinguserSelect;
  }

  async getCblockinguserSelectedOption() {
    return await this.cblockinguserSelect.element(by.css('option:checked')).getText();
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

export class BlockuserDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-blockuser-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-blockuser'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
