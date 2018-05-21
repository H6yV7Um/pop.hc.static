package com.jd.help.customer.web.vo;

import com.jd.pop.form.api.open.dto.FormDTO;
import com.jd.pop.form.api.open.dto.FormElementDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lipengfei5 on 2017/9/27.
 */
public class FormVo implements Serializable{
    private Form form;
    private List<FormElement> formElementList;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<FormElement> getFormElementList() {
        return formElementList;
    }

    public void setFormElementList(List<FormElement> formElementList) {
        this.formElementList = formElementList;
    }
}
