package com.muazam.tech.smartinventoryrfid20.Utils;

public class GetJSONResponse {
    public String GetJSONResponse(String res) {
        try {
            res = res.trim();
            res = res.trim().replace("\\", "");
            res = res.trim().replace("\n", "");
            res = res.replace("\"{\"Table\"", "{\"Table\"");


            if (!res.contains("\"JSONData\":\"null\"")) {
                for (int i = res.length() - 1; i > 0; i--) {
                    String a = res.charAt(i) + "";
                    StringBuilder s = new StringBuilder(res);
                    if (a.equals("\"")) {
                        s.setCharAt(i, ' ');
                        return s.toString();
                    }
                }
            } else {
                return res;
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "null";
    }
}
