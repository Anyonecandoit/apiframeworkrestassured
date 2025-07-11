package sa.com.kranthi.e2eAutomation.backend.apis.internationRemmitance;


import  sa.com.kranthi.e2eAutomation.backend.apis.commonAPIUtil.IRClient.IRUtils;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import sa.com.kranthi.e2eAutomation.backend.apis.commonAPIUtils;
import sa.com.kranthi.e2eAutomation.backend.apis.asserts.CommonAsserts;
import sa.com.kranthi.e2eAutomation.backend.apis.commonAPIUtil.IRUtils;
import sa.com.kranthi.e2eAutomation.backend.apis.commonAPIUtil.IRClients.FSWebhookClient;
import sa.com.kranthi.e2eAutomation.backend.apis.commonAPIUtil.IRClients.MGWebhookClient;
import sa.com.kranthi.e2eAutomation.backend.apis.commonAPIUtil.IRClients.SASAPIClient;
import sa.com.kranthi.e2eAutomation.backend.apis.commonAPIUtil.IRClients.TPWebhookClient;
import sa.com.kranthi.e2eAutomation.backend.apis.report.AssertAndLog;
import sa.com.kranthi.e2eAutomation.backend.foundation.baseConfig.BaseTest;
import sa.com.kranthi.e2eAutomation.backend.foundation.baseConfig.DataProviders;
import sa.com.kranthi.e2eAutomation.backend.foundation.mysqlConfig.DBMethods;
import sa.com.kranthi.e2eAutomation.backend.foundation.mysqlConfig.DBQueries;

public class CompleteFlowTests extends BaseTest {
    /**
     * This test class contains tests for International Remittance Service.
     * It verifies the Complete flow of Transactions which includes the third party
     * webhooks as well depending on the
     * test Data configured.
     *
     * @param: testData - Test Data for the API test (from testJson Data provider)
     *
     * @author:
     */
    @Test(description = "IR Complete Flow Test", dataProvider = "testJson", dataProviderClass = DataProviders.class)
    public void CompleteFlow(String testData) {
        CommonAsserts ca = new CommonAsserts();
        JSONObject testObj = new JSONObject(testData);
        AssertAndLog.INFO("Test Case name is: " + testObj.getString("name") + " and testData is: " + testData);

//		Update the Required MTO as Active and rest as Inactive.
        IRUtils.updateMTO(testData);
//		Update the CB Wallet Limit.
        IRUtils.updateCBlimit();
//		Update the User's Wallet Balance.
        IRUtils.updateWalletBalance();
//		Save a new beneficiary based on the Test Data.
        int bnvId = 0;
        bnvId = IRUtils.saveBeneficiary(IRUtils.updateTestDataForCreateBeneficiary(testData));
        AssertAndLog.INFO("Created bnvID is: " + bnvId);

//		Create transaction for the above created beneficiary.
        String txnId = "";
        if ( testObj.has("sasHold") )
            txnId = IRUtils.createIRTransaction(bnvId, 501);
        else
            txnId = IRUtils.createIRTransaction(bnvId);

        AssertAndLog.INFO("Created txnID is: " + txnId);
        IRUtils.checkAndSendFSEventIfRequired(txnId,testObj);
        ca.assertTrue(txnId.contains("RMT"), "The transaction reference Id should contain RMT");
        Map<String, Object> expectedStatus = commonAPIUtils.jsonToMap(testObj.getJSONObject("commitStatus"));;
        AssertAndLog.INFO("Expected Status after txn create: " + expectedStatus);
        Map<String, Object> commitStatus = new HashMap<>();
        for (int i = 0; i < 6; i++) {
//			This loop is added so that we do not force a long wait here.
//			It checks for the Status updates every 10 sec and if the updates are done, it comes out of the loop.
            commonAPIUtils.waitForUpdates(10000);
//			Get the Actual Txn Status from DB after commit and expected Status from Test Data and compare.
            commitStatus = db.getTxnStatus(String.format(DBQueries.GET_IR_TXN_STATUS, txnId));
            if( commitStatus.equals(expectedStatus) )
                break;
            else if(i==5)
                AssertAndLog.WARNING("Status not updated in DB after 60 sec");
        }
        AssertAndLog.INFO("Actual Status after transaction commit: " + commitStatus);
        ca.hashMapAssert(commitStatus, expectedStatus);
//		Send Compliance webhooks if required.
        if ( testObj.has("sasHold") || testObj.has("fsHold") ) {
//			SAS Webhook
            if ( testObj.has("sasHold") ) {
                String sasTxnId = (String) DBMethods.getSingleValue(String.format(DBQueries.GET_IR_TXN_SAS_ID, txnId));
                SASAPIClient sas = new SASAPIClient();
                sas.sasWebhook(txnId, sasTxnId, testObj.getBoolean("sasHold"));
            }
//			FinScan Webhook
            if ( testObj.has("fsHold") ) {
                FSWebhookClient fs = new FSWebhookClient();
                fs.fsAction(txnId, testObj.getBoolean("fsHold"));
            }
//			Get the Actual Txn Status from DB after commit and expected Status from Test Data and compare.
            expectedStatus = commonAPIUtils.jsonToMap(testObj.getJSONObject("afterCompStatus"));
            AssertAndLog.INFO("Expected Status After Compliance webhooks: " + expectedStatus);
            Map<String, Object> afterCompStatus = db.getTxnStatus(String.format(DBQueries.GET_IR_TXN_STATUS, txnId));
            AssertAndLog.INFO("Actual Status After Compliance webhooks: " + afterCompStatus);
            ca.assertEquals(afterCompStatus, expectedStatus, "Status not matching");
        }
        if(testObj.has("userCancel")){
            IRUtils.cancelTransaction(txnId);
        }
        else {
            Map<String, Object> txnDetails = db.getMultipleKeys(String.format(DBQueries.GET_IR_TXN_DETAILS, txnId));
            if (txnDetails.get("mto_txn_reference_number") != null) {
                String mtcn = (String) DBMethods.getSingleValue(String.format(DBQueries.GET_IR_TXN_MTCN, txnId));
                JSONArray whArr = testObj.getJSONArray("mtoWebhooks");
//				Sending the MTO Webhooks.
                for (Object o : whArr) {
                    JSONObject whObj = new JSONObject(o.toString());
                    if (testObj.getString("mto")
                            .equals("MoneyGram")) {
                        MGWebhookClient mg = new MGWebhookClient();
                        mg.mgAction(txnId, mtcn, whObj.getString("whStatus"));
                    } else if (testObj.getString("mto")
                            .equals("TerraPay")) {
                        TPWebhookClient tp = new TPWebhookClient();
                        tp.tpWhAction(txnId, mtcn, whObj.getString("whStatus"));
                    }
//				Get the Actual Txn Status from DB after commit and expected Status from Test Data and compare.
                    Map<String, Object> actualStatus = db.getTxnStatus(
                            String.format(DBQueries.GET_IR_TXN_STATUS, txnId));
                    AssertAndLog.INFO("Actual Status after MTO Webhooks: " + actualStatus);
                    expectedStatus = commonAPIUtils.jsonToMap(whObj.getJSONObject("expectedStatus"));
                    AssertAndLog.INFO(
                            "Expected Status After " + whObj.getString("whStatus") + " webhook are: " + expectedStatus);
                    ca.hashMapAssert(actualStatus, expectedStatus);
                }
            }
        }
    }

//	@BeforeClass
//	public static void getNewUserDetails() {
//		if ( !Boolean.getBoolean("oldUser") && HeaderConfigs.userDetails == null ) {
//			System.out.println("in create user");
//			HeaderConfigs.userDetails = AccountsUtil.userRegisteration(db);
//		}
//	}
}