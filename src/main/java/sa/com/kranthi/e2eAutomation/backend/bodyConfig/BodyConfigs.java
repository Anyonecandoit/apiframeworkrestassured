package sa.com.kranthi.e2eAutomation.backend.bodyConfig;

import org.json.simple.JSONObject;

public class BodyConfigs {





    public static JSONObject irUpdateVendor(String mtoId, boolean status){


        JSONObject updateVendor = new JSONObject();

        updateVendor.put("mtoId", mtoId);
        updateVendor.put("status", status);
        return updateVendor;
    }
}
