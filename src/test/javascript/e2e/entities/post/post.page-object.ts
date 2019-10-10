import { element, by, ElementFinder } from 'protractor';

export class PostComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-post div table .btn-danger'));
  title = element.all(by.css('jhi-post div h2#page-heading span')).first();

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

export class PostUpdatePage {
  pageTitle = element(by.id('jhi-post-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  publicationDateInput = element(by.id('field_publicationDate'));
  headlineInput = element(by.id('field_headline'));
  leadtextInput = element(by.id('field_leadtext'));
  bodytextInput = element(by.id('field_bodytext'));
  quoteInput = element(by.id('field_quote'));
  conclusionInput = element(by.id('field_conclusion'));
  linkTextInput = element(by.id('field_linkText'));
  linkURLInput = element(by.id('field_linkURL'));
  imageInput = element(by.id('file_image'));
  appuserSelect = element(by.id('field_appuser'));
  blogSelect = element(by.id('field_blog'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return await this.creationDateInput.getAttribute('value');
  }

  async setPublicationDateInput(publicationDate) {
    await this.publicationDateInput.sendKeys(publicationDate);
  }

  async getPublicationDateInput() {
    return await this.publicationDateInput.getAttribute('value');
  }

  async setHeadlineInput(headline) {
    await this.headlineInput.sendKeys(headline);
  }

  async getHeadlineInput() {
    return await this.headlineInput.getAttribute('value');
  }

  async setLeadtextInput(leadtext) {
    await this.leadtextInput.sendKeys(leadtext);
  }

  async getLeadtextInput() {
    return await this.leadtextInput.getAttribute('value');
  }

  async setBodytextInput(bodytext) {
    await this.bodytextInput.sendKeys(bodytext);
  }

  async getBodytextInput() {
    return await this.bodytextInput.getAttribute('value');
  }

  async setQuoteInput(quote) {
    await this.quoteInput.sendKeys(quote);
  }

  async getQuoteInput() {
    return await this.quoteInput.getAttribute('value');
  }

  async setConclusionInput(conclusion) {
    await this.conclusionInput.sendKeys(conclusion);
  }

  async getConclusionInput() {
    return await this.conclusionInput.getAttribute('value');
  }

  async setLinkTextInput(linkText) {
    await this.linkTextInput.sendKeys(linkText);
  }

  async getLinkTextInput() {
    return await this.linkTextInput.getAttribute('value');
  }

  async setLinkURLInput(linkURL) {
    await this.linkURLInput.sendKeys(linkURL);
  }

  async getLinkURLInput() {
    return await this.linkURLInput.getAttribute('value');
  }

  async setImageInput(image) {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput() {
    return await this.imageInput.getAttribute('value');
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

  async blogSelectLastOption(timeout?: number) {
    await this.blogSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async blogSelectOption(option) {
    await this.blogSelect.sendKeys(option);
  }

  getBlogSelect(): ElementFinder {
    return this.blogSelect;
  }

  async getBlogSelectedOption() {
    return await this.blogSelect.element(by.css('option:checked')).getText();
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

export class PostDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-post-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-post'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
