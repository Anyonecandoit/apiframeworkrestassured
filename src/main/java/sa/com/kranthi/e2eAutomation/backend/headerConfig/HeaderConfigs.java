package sa.com.kranthi.e2eAutomation.backend.headerConfig;

import java.util.HashMap;
import java.util.Map;

public class HeaderConfigs {




    public static Map<String, Object> loginHeaders() { // @kranthi +1
        Map<String, Object> defaultHeaders = new HashMap<>();

        defaultHeaders.put("x-baccount-no", "991185475747262");
        defaultHeaders.put("Content-Type", "application/json");
        defaultHeaders.put("x-self-status", "ACTIVE");
        defaultHeaders.put("x-account-status", "ACTIVE");
        defaultHeaders.put("x-buser-id", "d162e215-31ea-46ed-b387-aca9e8681d5");
        defaultHeaders.put("x-baccount-id", "991185475747262");
        defaultHeaders.put("x-res-ip", "192.168.6.112");
        defaultHeaders.put("x-b-country-iso", "IND");
        defaultHeaders.put("x-bapp-country-id", "IND");
        defaultHeaders.put("x-bdevice-id", "2e7ba81b6fbb7ae");
        defaultHeaders.put("x-latitude", "18.512788");
        defaultHeaders.put("x-longitude", "73.867088");
        defaultHeaders.put("x-btype", "1%"); // This still looks like a potential typo, '1%'
        defaultHeaders.put("x-bdevice-platform", "Android");
        defaultHeaders.put("Content-Type", "application/json"); // Duplicate key, will overwrite the previous one

        if (userDetails != null) { // Assuming userDetails is an object accessible here
            defaultHeaders.put("x-baccount-no", userDetails.get("accountNumber"));
            defaultHeaders.put("x-b-cid", userDetails.get("cid"));
            defaultHeaders.put("x-buser-id", userDetails.get("userId"));
            defaultHeaders.put("x-baccount-id", userDetails.get("accountNumber")); // Reusing accountNumber for accountId?
            defaultHeaders.put("x-bdevice-id", "123456789"); // This line appears to be cut off, but "123456789" is visible.
        }

        return defaultHeaders;
    }


    // methods to pass in sendrequest



   // irDashboardVendor();


    public static Map<String, Object> irDashboardVendor(){

        Map<String, Object> defaultHeaders = new HashMap<String, Object>();

        defaultHeaders.put("Content-Type", "application/json");

        return defaultHeaders;



    }


}
