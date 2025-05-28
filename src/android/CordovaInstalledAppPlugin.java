package cordova.plugins;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class CordovaInstalledAppPlugin extends CordovaPlugin {
    public CallbackContext callback = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getInstalledAppList")) {
            callback = callbackContext;
            getInstalledAppList(action, callbackContext);
            return true;
        }
        return false;
    }

    private void getInstalledAppList(String message, CallbackContext callbackContext) {
      cordova.getThreadPool().execute(new Runnable() {
        @Override
        public void run() {
          try {
            PackageManager pm = cordova.getActivity().getPackageManager();
            List<PackageInfo> packages = pm.getInstalledPackages(0);
            ArrayList<String> installedApps = new ArrayList<String>();
            for (PackageInfo packageInfo : packages) {
              String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
              String packageName = packageInfo.packageName;
              installedApps.add(appName + " (" + packageName + ")");
            }
            callback.success(new JSONArray(installedApps));
          } catch(Exception e) {
            Log.e("CordovaInstalledAppPlugin", "Error getting installed apps", e);
            callbackContext.error("Error getting installed apps: " + e.getMessage());
          }
        }
      });
    }
}