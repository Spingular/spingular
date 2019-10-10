import { element, by, ElementFinder } from 'protractor';

export class AppprofileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-appprofile div table .btn-danger'));
  title = element.all(by.css('jhi-appprofile div h2#page-heading span')).first();

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

export class AppprofileUpdatePage {
  pageTitle = element(by.id('jhi-appprofile-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  creationDateInput = element(by.id('field_creationDate'));
  genderSelect = element(by.id('field_gender'));
  phoneInput = element(by.id('field_phone'));
  bioInput = element(by.id('field_bio'));
  facebookInput = element(by.id('field_facebook'));
  twitterInput = element(by.id('field_twitter'));
  linkedinInput = element(by.id('field_linkedin'));
  instagramInput = element(by.id('field_instagram'));
  googlePlusInput = element(by.id('field_googlePlus'));
  birthdateInput = element(by.id('field_birthdate'));
  civilStatusSelect = element(by.id('field_civilStatus'));
  lookingForSelect = element(by.id('field_lookingFor'));
  purposeSelect = element(by.id('field_purpose'));
  physicalSelect = element(by.id('field_physical'));
  religionSelect = element(by.id('field_religion'));
  ethnicGroupSelect = element(by.id('field_ethnicGroup'));
  studiesSelect = element(by.id('field_studies'));
  sibblingsInput = element(by.id('field_sibblings'));
  eyesSelect = element(by.id('field_eyes'));
  smokerSelect = element(by.id('field_smoker'));
  childrenSelect = element(by.id('field_children'));
  futureChildrenSelect = element(by.id('field_futureChildren'));
  petInput = element(by.id('field_pet'));
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

  async setGenderSelect(gender) {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect() {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(timeout?: number) {
    await this.genderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPhoneInput(phone) {
    await this.phoneInput.sendKeys(phone);
  }

  async getPhoneInput() {
    return await this.phoneInput.getAttribute('value');
  }

  async setBioInput(bio) {
    await this.bioInput.sendKeys(bio);
  }

  async getBioInput() {
    return await this.bioInput.getAttribute('value');
  }

  async setFacebookInput(facebook) {
    await this.facebookInput.sendKeys(facebook);
  }

  async getFacebookInput() {
    return await this.facebookInput.getAttribute('value');
  }

  async setTwitterInput(twitter) {
    await this.twitterInput.sendKeys(twitter);
  }

  async getTwitterInput() {
    return await this.twitterInput.getAttribute('value');
  }

  async setLinkedinInput(linkedin) {
    await this.linkedinInput.sendKeys(linkedin);
  }

  async getLinkedinInput() {
    return await this.linkedinInput.getAttribute('value');
  }

  async setInstagramInput(instagram) {
    await this.instagramInput.sendKeys(instagram);
  }

  async getInstagramInput() {
    return await this.instagramInput.getAttribute('value');
  }

  async setGooglePlusInput(googlePlus) {
    await this.googlePlusInput.sendKeys(googlePlus);
  }

  async getGooglePlusInput() {
    return await this.googlePlusInput.getAttribute('value');
  }

  async setBirthdateInput(birthdate) {
    await this.birthdateInput.sendKeys(birthdate);
  }

  async getBirthdateInput() {
    return await this.birthdateInput.getAttribute('value');
  }

  async setCivilStatusSelect(civilStatus) {
    await this.civilStatusSelect.sendKeys(civilStatus);
  }

  async getCivilStatusSelect() {
    return await this.civilStatusSelect.element(by.css('option:checked')).getText();
  }

  async civilStatusSelectLastOption(timeout?: number) {
    await this.civilStatusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setLookingForSelect(lookingFor) {
    await this.lookingForSelect.sendKeys(lookingFor);
  }

  async getLookingForSelect() {
    return await this.lookingForSelect.element(by.css('option:checked')).getText();
  }

  async lookingForSelectLastOption(timeout?: number) {
    await this.lookingForSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPurposeSelect(purpose) {
    await this.purposeSelect.sendKeys(purpose);
  }

  async getPurposeSelect() {
    return await this.purposeSelect.element(by.css('option:checked')).getText();
  }

  async purposeSelectLastOption(timeout?: number) {
    await this.purposeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPhysicalSelect(physical) {
    await this.physicalSelect.sendKeys(physical);
  }

  async getPhysicalSelect() {
    return await this.physicalSelect.element(by.css('option:checked')).getText();
  }

  async physicalSelectLastOption(timeout?: number) {
    await this.physicalSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setReligionSelect(religion) {
    await this.religionSelect.sendKeys(religion);
  }

  async getReligionSelect() {
    return await this.religionSelect.element(by.css('option:checked')).getText();
  }

  async religionSelectLastOption(timeout?: number) {
    await this.religionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setEthnicGroupSelect(ethnicGroup) {
    await this.ethnicGroupSelect.sendKeys(ethnicGroup);
  }

  async getEthnicGroupSelect() {
    return await this.ethnicGroupSelect.element(by.css('option:checked')).getText();
  }

  async ethnicGroupSelectLastOption(timeout?: number) {
    await this.ethnicGroupSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setStudiesSelect(studies) {
    await this.studiesSelect.sendKeys(studies);
  }

  async getStudiesSelect() {
    return await this.studiesSelect.element(by.css('option:checked')).getText();
  }

  async studiesSelectLastOption(timeout?: number) {
    await this.studiesSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setSibblingsInput(sibblings) {
    await this.sibblingsInput.sendKeys(sibblings);
  }

  async getSibblingsInput() {
    return await this.sibblingsInput.getAttribute('value');
  }

  async setEyesSelect(eyes) {
    await this.eyesSelect.sendKeys(eyes);
  }

  async getEyesSelect() {
    return await this.eyesSelect.element(by.css('option:checked')).getText();
  }

  async eyesSelectLastOption(timeout?: number) {
    await this.eyesSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setSmokerSelect(smoker) {
    await this.smokerSelect.sendKeys(smoker);
  }

  async getSmokerSelect() {
    return await this.smokerSelect.element(by.css('option:checked')).getText();
  }

  async smokerSelectLastOption(timeout?: number) {
    await this.smokerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setChildrenSelect(children) {
    await this.childrenSelect.sendKeys(children);
  }

  async getChildrenSelect() {
    return await this.childrenSelect.element(by.css('option:checked')).getText();
  }

  async childrenSelectLastOption(timeout?: number) {
    await this.childrenSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setFutureChildrenSelect(futureChildren) {
    await this.futureChildrenSelect.sendKeys(futureChildren);
  }

  async getFutureChildrenSelect() {
    return await this.futureChildrenSelect.element(by.css('option:checked')).getText();
  }

  async futureChildrenSelectLastOption(timeout?: number) {
    await this.futureChildrenSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  getPetInput(timeout?: number) {
    return this.petInput;
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

export class AppprofileDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-appprofile-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-appprofile'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
