package com.muazam.tech.smartinventory20.Utils;



import com.muazam.tech.smartinventory20.Model.ModelParams;

import java.util.ArrayList;

public class ParamGetter {


    public static String getValue(ArrayList<ModelParams> lst_params) {
        StringBuilder value= new StringBuilder("?");
        for (int i = 0; i < lst_params.size(); i++) {
            if (i == lst_params.size() - 1) {
                value.append(lst_params.get(i).name).append("=").append(lst_params.get(i).value);
            } else {
                value.append(lst_params.get(i).name).append("=").append(lst_params.get(i).value).append("&");
            }
        }
        return value.toString();
    }
}
