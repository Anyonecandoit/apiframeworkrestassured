package sa.com.kranthi.e2eAutomation.backend.apis.commonAPIUtil.IRClient;


import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sa.com.kranthi.e2eAutomation.backend.apiConfig.pathConfig.apiPath.ApiPath;
import sa.com.kranthi.e2eAutomation.backend.bodyConfig.BodyConfigs;
import sa.com.kranthi.e2eAutomation.backend.foundation.baseConfig.BaseTest;
import sa.com.kranthi.e2eAutomation.backend.headerConfig.HeaderConfigs;

import sa.com.kranthi.e2eAutomation.backend.foundation.mysqlConfigs.DBMethods;
import sa.com.kranthi.e2eAutomation.backend.foundation.mysqlConfigs.DBQueries;

public class IRUtils  extends BaseTest {


    /**
     * Updates the MTO Provider status for a given test data.
     * @author kranthi
     * @param testData the test data containing the MTO information.
     */
    public static void updateMTO(String testData) {


        JSONObject testObj = new JSONObject(testData);
        vendorsStatus = getVendorStatusList();
        AssertAndLog.INFO("Updating " + testObj.getString("mto") + " as " + true + " and rest as false.");
        for ( int i = 0; i < vendorsStatus.length(); i++ ) {
            JSONObject vendor = vendorsStatus.getJSONObject(i);
            if ( vendor.getString("name").equals(testObj.getString("mto")) )
                commonAPIUtils.sendRequest(Method.POST, ApiPath.OPERATOR.DASHBOARD_VENDOR,
                        HeaderConfigs.irDashboardVendor(),
                        BodyConfigs.irUpdateVendor(vendor.getString("id"), true).toString(), 201);
            else
                commonAPIUtils.sendRequest(Method.POST, ApiPath.OPERATOR.DASHBOARD_VENDOR,
                        HeaderConfigs.irDashboardVendor(),
                        BodyConfigs.irUpdateVendor(vendor.getString("id"), false).toString(), 201);
        }
        commonAPIUtils.waitForUpdates(2000);
    }



    public static JSONArray getVendorStatusList() {


        Response vendorStatusResp = commonAPIUtils.sendRequest(Method.GET, ApiPath.OPERATOR.DASHBOARD_VENDOR,
                HeaderConfigs.irDashboardVendor(), null, 200);





        JSONArray vendorStatus = new JSONObject(vendorStatusResp.getBody().asString()).getJSONObject("data")
                .getJSONArray("items");
        AssertAndLog.INFO("Current Vendor Status is: " + vendorStatus);

        return vendorStatus;
    }



    public static  void updateCBlimit(){

      String accountNo =   HeaderConfigs.loginHeaders().get("x-baccount-no").toString();

    String agentTxnData =   String.format(DBQueries.UPDATE_CB_LIMIT, accountNo);

    DBMethods.updateDB(agentTxnData);


    }


}
