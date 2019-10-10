// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  NotificationComponentsPage,
  /* NotificationDeleteDialog,
   */ NotificationUpdatePage
} from './notification.page-object';

const expect = chai.expect;

describe('Notification e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notificationComponentsPage: NotificationComponentsPage;
  let notificationUpdatePage: NotificationUpdatePage;
  /* let notificationDeleteDialog: NotificationDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Notifications', async () => {
    await navBarPage.goToEntity('notification');
    notificationComponentsPage = new NotificationComponentsPage();
    await browser.wait(ec.visibilityOf(notificationComponentsPage.title), 5000);
    expect(await notificationComponentsPage.getTitle()).to.eq('spingularApp.notification.home.title');
  });

  it('should load create Notification page', async () => {
    await notificationComponentsPage.clickOnCreateButton();
    notificationUpdatePage = new NotificationUpdatePage();
    expect(await notificationUpdatePage.getPageTitle()).to.eq('spingularApp.notification.home.createOrEditLabel');
    await notificationUpdatePage.cancel();
  });

  /*  it('should create and save Notifications', async () => {
        const nbButtonsBeforeCreate = await notificationComponentsPage.countDeleteButtons();

        await notificationComponentsPage.clickOnCreateButton();
        await promise.all([
            notificationUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            notificationUpdatePage.setNotificationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            notificationUpdatePage.notificationReasonSelectLastOption(),
            notificationUpdatePage.setNotificationTextInput('notificationText'),
            notificationUpdatePage.appuserSelectLastOption(),
        ]);
        expect(await notificationUpdatePage.getCreationDateInput()).to.contain('2001-01-01T02:30', 'Expected creationDate value to be equals to 2000-12-31');
        expect(await notificationUpdatePage.getNotificationDateInput()).to.contain('2001-01-01T02:30', 'Expected notificationDate value to be equals to 2000-12-31');
        expect(await notificationUpdatePage.getNotificationTextInput()).to.eq('notificationText', 'Expected NotificationText value to be equals to notificationText');
        const selectedIsDelivered = notificationUpdatePage.getIsDeliveredInput();
        if (await selectedIsDelivered.isSelected()) {
            await notificationUpdatePage.getIsDeliveredInput().click();
            expect(await notificationUpdatePage.getIsDeliveredInput().isSelected(), 'Expected isDelivered not to be selected').to.be.false;
        } else {
            await notificationUpdatePage.getIsDeliveredInput().click();
            expect(await notificationUpdatePage.getIsDeliveredInput().isSelected(), 'Expected isDelivered to be selected').to.be.true;
        }
        await notificationUpdatePage.save();
        expect(await notificationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Notification', async () => {
        const nbButtonsBeforeDelete = await notificationComponentsPage.countDeleteButtons();
        await notificationComponentsPage.clickOnLastDeleteButton();

        notificationDeleteDialog = new NotificationDeleteDialog();
        expect(await notificationDeleteDialog.getDialogTitle())
            .to.eq('spingularApp.notification.delete.question');
        await notificationDeleteDialog.clickOnConfirmButton();

        expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
